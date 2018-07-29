package org.librehealth.fhir.analytics.cassandra;

import com.datastax.driver.core.Session;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;

public interface CassandraDataService {

    void insertDemoData(Session session) throws LibreHealthFHIRAnalyticsException;
    void loadAllData(JavaSparkContext sc, SparkSession sparkSession);
    void loadPatients(JavaSparkContext sc, SparkSession sparkSession);
    void loadObservations(JavaSparkContext sc, SparkSession sparkSession);
    void loadHealthcareServices(JavaSparkContext sc, SparkSession sparkSession);
    void loadAccounts(JavaSparkContext sc, SparkSession sparkSession);
    void loadDeviceRequests(JavaSparkContext sc, SparkSession sparkSession);
    void loadSupplyRequestss(JavaSparkContext sc, SparkSession sparkSession);
    void loadReferralRequests(JavaSparkContext sc, SparkSession sparkSession);
    void loadPractitioners(JavaSparkContext sc, SparkSession sparkSession);
    void loadMeasureReports(JavaSparkContext sc, SparkSession sparkSession);
    void loadCarePlans(JavaSparkContext sc, SparkSession sparkSession);
    void loadLocations(JavaSparkContext sc, SparkSession sparkSession);
    void loadSchedules(JavaSparkContext sc, SparkSession sparkSession);
    void loadEncounters(JavaSparkContext sc, SparkSession sparkSession);
    void loadMedications(JavaSparkContext sc, SparkSession sparkSession);
    void loadRiskAssessments(JavaSparkContext sc, SparkSession sparkSession);
    void loadEnrolementRequests(JavaSparkContext sc, SparkSession sparkSession);
    void loadMedicationRequests(JavaSparkContext sc, SparkSession sparkSession);
    void loadMedicationStatements(JavaSparkContext sc, SparkSession sparkSession);
    void loadMedicationDispenses(JavaSparkContext sc, SparkSession sparkSession);
    void loadSubstances(JavaSparkContext sc, SparkSession sparkSession);
    void loadImmunizationRecommendations(JavaSparkContext sc, SparkSession sparkSession);
    void loadSpecimens(JavaSparkContext sc, SparkSession sparkSession);
    void loadQuestionaires(JavaSparkContext sc, SparkSession sparkSession);
    void loadAdverseEvents(JavaSparkContext sc, SparkSession sparkSession);
    void loadEligibilityRequests(JavaSparkContext sc, SparkSession sparkSession);
    void loadContracts(JavaSparkContext sc, SparkSession sparkSession);
    void loadRelatedPersons(JavaSparkContext sc, SparkSession sparkSession);
    void loadImagingStudies(JavaSparkContext sc, SparkSession sparkSession);
    void loadPaymentNotices(JavaSparkContext sc, SparkSession sparkSession);
    void loadProcessRequests(JavaSparkContext sc, SparkSession sparkSession);
    void loadSubscriptions(JavaSparkContext sc, SparkSession sparkSession);
    void loadFamilyMemberHistories(JavaSparkContext sc, SparkSession sparkSession);
    void loadLibraries(JavaSparkContext sc, SparkSession sparkSession);
    void loadAllergyIntolerences(JavaSparkContext sc, SparkSession sparkSession);
    void loadNutritionOrders(JavaSparkContext sc, SparkSession sparkSession);
    void loadImagineManifests(JavaSparkContext sc, SparkSession sparkSession);
    void loadPersons(JavaSparkContext sc, SparkSession sparkSession);
    void loadImmunizations(JavaSparkContext sc, SparkSession sparkSession);
    void loadVisionPrescriptios(JavaSparkContext sc, SparkSession sparkSession);
    void loadAppointments(JavaSparkContext sc, SparkSession sparkSession);
    void loadDiagnosticReports(JavaSparkContext sc, SparkSession sparkSession);
    void loadConditions(JavaSparkContext sc, SparkSession sparkSession);
    void loadOrganizations(JavaSparkContext sc, SparkSession sparkSession);
    void loadProcedures(JavaSparkContext sc, SparkSession sparkSession);
    void loadDevices(JavaSparkContext sc, SparkSession sparkSession);
    void insertData(Session session, String resource);
}
