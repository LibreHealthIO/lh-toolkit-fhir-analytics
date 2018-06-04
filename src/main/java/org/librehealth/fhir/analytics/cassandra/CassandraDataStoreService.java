package org.librehealth.fhir.analytics.cassandra;

import com.datastax.driver.core.Session;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;

public interface CassandraDataStoreService {
    void init(Session session);

    void insertSampleData(Session session) throws LibreHealthFHIRAnalyticsException;

    void preloadData(JavaSparkContext sc, SparkSession sparkSession);
}
