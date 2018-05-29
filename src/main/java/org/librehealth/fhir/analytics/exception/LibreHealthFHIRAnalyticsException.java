package org.librehealth.fhir.analytics.exception;

public class LibreHealthFHIRAnalyticsException extends Exception {

    public LibreHealthFHIRAnalyticsException(String message, Throwable e) {
        super(message, e);
    }

    public LibreHealthFHIRAnalyticsException(String message) {
        super(message);
    }
}
