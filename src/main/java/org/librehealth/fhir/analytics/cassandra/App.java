package org.librehealth.fhir.analytics.cassandra;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.librehealth.fhir.analytics.LibreHealthFHIRAnalyticsExecutionManager;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.utils.LibrehealthAnalyticsUtils;

import java.io.Serializable;

public class App implements Serializable {

    public static void main(String[] args) throws LibreHealthFHIRAnalyticsException, JsonProcessingException {
        String query ="SELECT * " +
                "FROM patient inner join observation where observation.subject.reference == patient.id";
        LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
        System.out.println(LibrehealthAnalyticsUtils.executeSql(query, manager.getSparkSession()));
    }
}
