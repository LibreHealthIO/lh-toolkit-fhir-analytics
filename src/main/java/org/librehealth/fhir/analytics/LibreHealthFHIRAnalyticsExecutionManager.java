package org.librehealth.fhir.analytics;

import com.datastax.spark.connector.cql.CassandraConnector;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class LibreHealthFHIRAnalyticsExecutionManager {
    private SparkConf sparkConf;
    private SparkSession sparkSession;
    private JavaSparkContext javaSparkContext;

    public CassandraConnector getCassandraConnector() {
        return cassandraConnector;
    }

    public void setCassandraConnector(CassandraConnector cassandraConnector) {
        this.cassandraConnector = cassandraConnector;
    }

    private CassandraConnector cassandraConnector;
    private static LibreHealthFHIRAnalyticsExecutionManager instance;

    public SparkConf getSparkConf() {
        return sparkConf;
    }

    public void setSparkConf(SparkConf sparkConf) {
        this.sparkConf = sparkConf;
    }

    public SparkSession getSparkSession() {
        return sparkSession;
    }

    public void setSparkSession(SparkSession sparkSession) {
        this.sparkSession = sparkSession;
    }

    public JavaSparkContext getJavaSparkContext() {
        return javaSparkContext;
    }

    public void setJavaSparkContext(JavaSparkContext javaSparkContext) {
        this.javaSparkContext = javaSparkContext;
    }

    private LibreHealthFHIRAnalyticsExecutionManager() {
        this.sparkConf = new SparkConf()
                .setAppName("Spark Librehealth FHIR Analytics")
                .setMaster("local[*]")
                .set("spark.cassandra.connection.host", "127.0.0.1");
        this.javaSparkContext = new JavaSparkContext(sparkConf);
        this.sparkSession = SparkSession
                .builder()
                .appName("Spark Librehealth FHIR Analytics")
                .config("spark.some.config.option", "fhir")
                .getOrCreate();
        this.cassandraConnector = CassandraConnector.apply(sparkConf);
    }

    public static LibreHealthFHIRAnalyticsExecutionManager getInstance() {
        if (instance == null) {
            synchronized (LibreHealthFHIRAnalyticsExecutionManager.class) {
                if (instance == null) {
                    instance = new LibreHealthFHIRAnalyticsExecutionManager();
                }
            }
        }
        return instance;
    }
}
