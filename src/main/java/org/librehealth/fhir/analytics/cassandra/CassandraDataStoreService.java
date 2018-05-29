package org.librehealth.fhir.analytics.cassandra;

import com.datastax.driver.core.Session;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;

public interface CassandraDataStoreService {
    void init(Session session);

    void loadData(Session session) throws LibreHealthFHIRAnalyticsException;
}
