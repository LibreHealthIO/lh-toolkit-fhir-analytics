package org.librehealth.fhir.analytics.cassandra;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cerner.bunsen.FhirEncoders;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;

import java.io.Serializable;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

public class App implements Serializable {

    public static void main(String[] args) throws LibreHealthFHIRAnalyticsException {
        SparkConf conf = new SparkConf()
                .setAppName("Spark Librehealth FHIR Analytics")
                .setMaster("local[*]")
                .set("spark.cassandra.connection.host", "127.0.0.1");
        JavaSparkContext sc = new JavaSparkContext(conf);
        CassandraConnector connector = CassandraConnector.apply(conf);

        CassandraDataStoreService cassandraDataStoreService = new CassandraDataStoreServiceImpl();
        Session session = connector.openSession();
        cassandraDataStoreService.init(session);
        cassandraDataStoreService.loadData(session);

        SparkSession spark = SparkSession
                .builder()
                .appName("Spark Librehealth FHIR Analytics")
                .config("spark.some.config.option", "fhir")
                .getOrCreate();

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
        Dataset<Patient> peopleDFq = spark.createDataset(patientRDD.rdd(), encoders.of(Patient.class));
        Dataset<Observation> observationDfq = spark.createDataset(observationRDD.rdd(), encoders.of(Observation.class));

		peopleDFq.createOrReplaceTempView("patient");
        observationDfq.createOrReplaceTempView("observation");

		//Dataset<Row> sqlDF = spark.sql("SELECT * FROM patient where gender='male'");
		//sqlDF.show();

        Dataset<Row> obsDF = spark.sql("SELECT observation.meta, observation.subject, observation.specimen FROM patient inner join observation where observation.subject.reference == patient.id");
        obsDF.show(false);

        sc.stop();

    }
}
