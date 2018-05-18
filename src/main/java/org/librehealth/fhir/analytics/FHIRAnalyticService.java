package org.librehealth.fhir.analytics;

import com.cerner.bunsen.Bundles;
import com.cerner.bunsen.Bundles.BundleContainer;
import com.cerner.bunsen.FhirEncoders;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.Patient;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FHIRAnalyticService {

    private static Bundles bundles;
    private static JavaRDD<BundleContainer> bundlesRdd;

    public static String getAnalytics(String sparkSql) throws IOException {
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        SparkSession spark = SparkSession.builder()
                .master("local[*]")
                .appName("BundlesTest")
                .config("spark.sql.warehouse.dir",
                        Files.createTempDirectory("spark_warehouse").toString())
                .getOrCreate();

        bundles = Bundles.forStu3();

        bundlesRdd = bundles.loadFromDirectory(spark,
                "src/resources/data/xml/bundles", 1).cache();


        /*JavaRDD<Patient> patients = null;
        Dataset<Patient> patientDataset = spark.createDataset(patients,
                encoders.of(Condition.class));

        // Query for conditions based on arbitrary Spark SQL expressions
        Dataset<Patient> activePatients = patientDataset
                .where("clinicalStatus == 'active' and verificationStatus == 'confirmed'");

        // Count the query results
        long activeConditionCount = activePatients.count();

        // Convert the results back into a list of org.hl7.fhir.dstu3.model.Condition objects.
        List<Patient> retrievedPatientList = activePatients.collectAsList();*/
        JavaRDD<BundleContainer> bundless = bundles.loadFromDirectory(spark,
                "src/main/resources/data/json/bundles", 1);
        System.out.println(bundless.count());
        return null;
    }

    public static void main(String[] args) throws IOException {
        getAnalytics(null);
    }
}
