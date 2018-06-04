package org.librehealth.fhir.analytics.cassandra;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cerner.bunsen.FhirEncoders;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.utils.LibrehealthAnalyticsUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

public class CassandraDataStoreServiceImpl implements CassandraDataStoreService {

    private static final String dataPath = "data" + File.separator + "resources" + File.separator + "json";

    @Override
    public void init(Session session) {
        session.execute("CREATE KEYSPACE IF NOT EXISTS librehealth WITH " +
                "REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1}");
    }

    @Override
    public void insertSampleData(Session session) throws LibreHealthFHIRAnalyticsException {
        FhirContext fhirCtx = FhirContext.forDstu3();
        IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource(dataPath);
        String path = url.getPath();
        File[] files = new File(path).listFiles();
        String resource, resourceType;
        String cqlTableTemplate = "CREATE TABLE IF NOT EXISTS librehealth.%s (id varchar, value text, PRIMARY KEY(id));";
        String cqlInsertTemplate = "INSERT INTO librehealth.%s (ID, VALUE) values (?, ?) IF NOT EXISTS";
        Resource fhirResource;
        Bundle bundle;
        for (File file : files) {
            try {
                resource = LibrehealthAnalyticsUtils.readFileAsString(file);
                IBaseResource baseResource = parser.parseResource(resource);
                IIdType iIdType = baseResource.getIdElement();
                resourceType = iIdType.getResourceType();
                if (!(baseResource instanceof Bundle) && baseResource instanceof Resource) {
                    if(StringUtils.isEmpty(resourceType) || StringUtils.isEmpty(iIdType.getIdPart())) {
                        continue;
                    }
                    session.execute(String.format(cqlTableTemplate, resourceType.toLowerCase()));
                    PreparedStatement prepared = session.prepare(String.format(cqlInsertTemplate, resourceType.toLowerCase()));
                    BoundStatement bound = prepared.bind(iIdType.getIdPart(), resource);
                    session.execute(bound);
                } else {
                    bundle = (Bundle) baseResource;
                    for (Bundle.BundleEntryComponent entry : bundle.getEntry()) {
                        fhirResource = entry.getResource();
                        resourceType = fhirResource.getResourceType().name();
                        session.execute(String.format(cqlTableTemplate, resourceType.toLowerCase()));
                        PreparedStatement prepared = session.prepare(String.format(cqlInsertTemplate, resourceType.toLowerCase()));
                        BoundStatement bound = prepared.bind(fhirResource.getId(), parser.encodeResourceToString(fhirResource));
                        session.execute(bound);
                    }
                }
            } catch (IOException e) {
                throw new LibreHealthFHIRAnalyticsException("Error while reading data", e);
            }
        }
    }

    @Override
    public void preloadData(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Patient> patientRDD = javaFunctions(sc).cassandraTable("librehealth", "patient")
                .map((Function<CassandraRow, Patient>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
                    String patientSrt = cassandraRow.getString("value");
                    Patient patientOb = parser.parseResource(Patient.class, patientSrt);
                    return patientOb;
                });

        JavaRDD<Observation> observationRDD = javaFunctions(sc).cassandraTable("librehealth", "observation")
                .map((Function<CassandraRow, Observation>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
                    String observationStr = cassandraRow.getString("value");
                    Observation observationOb = parser.parseResource(Observation.class, observationStr);
                    return observationOb;
                });

        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Patient> peopleDFq = sparkSession.createDataset(patientRDD.rdd(), encoders.of(Patient.class));
        Dataset<Observation> observationDfq = sparkSession.createDataset(observationRDD.rdd(), encoders.of(Observation.class));

        peopleDFq.createOrReplaceTempView("patient");
        observationDfq.createOrReplaceTempView("observation");
    }
}
