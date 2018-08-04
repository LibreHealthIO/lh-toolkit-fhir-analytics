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
import org.hl7.fhir.dstu3.model.Account;
import org.hl7.fhir.dstu3.model.AdverseEvent;
import org.hl7.fhir.dstu3.model.AllergyIntolerance;
import org.hl7.fhir.dstu3.model.Appointment;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.CarePlan;
import org.hl7.fhir.dstu3.model.Condition;
import org.hl7.fhir.dstu3.model.Contract;
import org.hl7.fhir.dstu3.model.Device;
import org.hl7.fhir.dstu3.model.DeviceRequest;
import org.hl7.fhir.dstu3.model.DiagnosticReport;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.Encounter;
import org.hl7.fhir.dstu3.model.EnrollmentRequest;
import org.hl7.fhir.dstu3.model.FamilyMemberHistory;
import org.hl7.fhir.dstu3.model.HealthcareService;
import org.hl7.fhir.dstu3.model.ImagingManifest;
import org.hl7.fhir.dstu3.model.ImagingStudy;
import org.hl7.fhir.dstu3.model.Immunization;
import org.hl7.fhir.dstu3.model.ImmunizationRecommendation;
import org.hl7.fhir.dstu3.model.Library;
import org.hl7.fhir.dstu3.model.Location;
import org.hl7.fhir.dstu3.model.MeasureReport;
import org.hl7.fhir.dstu3.model.Medication;
import org.hl7.fhir.dstu3.model.MedicationDispense;
import org.hl7.fhir.dstu3.model.MedicationRequest;
import org.hl7.fhir.dstu3.model.MedicationStatement;
import org.hl7.fhir.dstu3.model.NutritionOrder;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Organization;
import org.hl7.fhir.dstu3.model.Patient;
import org.hl7.fhir.dstu3.model.PaymentNotice;
import org.hl7.fhir.dstu3.model.Person;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.hl7.fhir.dstu3.model.Procedure;
import org.hl7.fhir.dstu3.model.ProcessRequest;
import org.hl7.fhir.dstu3.model.Questionnaire;
import org.hl7.fhir.dstu3.model.ReferralRequest;
import org.hl7.fhir.dstu3.model.RelatedPerson;
import org.hl7.fhir.dstu3.model.Resource;
import org.hl7.fhir.dstu3.model.RiskAssessment;
import org.hl7.fhir.dstu3.model.Schedule;
import org.hl7.fhir.dstu3.model.Specimen;
import org.hl7.fhir.dstu3.model.Subscription;
import org.hl7.fhir.dstu3.model.Substance;
import org.hl7.fhir.dstu3.model.SupplyRequest;
import org.hl7.fhir.dstu3.model.VisionPrescription;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.instance.model.api.IIdType;
import org.librehealth.fhir.analytics.constants.LibreHealthAnalyticConstants;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.utils.LibrehealthAnalyticsUtils;
import org.librehealth.fhir.platform.model.CPatient;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

public class CassandraDataServiceImpl implements CassandraDataService {

    private static CassandraDataService service = null;

    private CassandraDataServiceImpl() {

    }

    public static CassandraDataService getInstance() {
        if (service == null) {
            synchronized (CassandraDataServiceImpl.class) {
                if (service == null) {
                    service = new CassandraDataServiceImpl();
                }
            }
        }
        return service;
    }

    @Override
    public void insertDemoData(Session session) throws LibreHealthFHIRAnalyticsException {
        session.execute(LibreHealthAnalyticConstants.CREATE_LIBRE_HEALTH_KEYSPACE);
        FhirContext fhirCtx = FhirContext.forDstu3();
        IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource(LibreHealthAnalyticConstants.DATA_PATH);
        String path = url.getPath();
        File[] files = new File(path).listFiles();
        String resource, resourceType;
        String cqlTableTemplate = LibreHealthAnalyticConstants.CREATE_TABLE_TEMPLATED_CQL;
        String cqlInsertTemplate = LibreHealthAnalyticConstants.INSERT_VALUES_TEMPLATED_CQL;
        Resource fhirResource;
        Bundle bundle;
        for (File file : files) {
            try {
                resource = LibrehealthAnalyticsUtils.readFileAsString(file);
                IBaseResource baseResource = parser.parseResource(resource);
                IIdType iIdType = baseResource.getIdElement();
                resourceType = iIdType.getResourceType();
                if (!(baseResource instanceof Bundle) && baseResource instanceof Resource) {
                    if (StringUtils.isEmpty(resourceType) || StringUtils.isEmpty(iIdType.getIdPart())) {
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
                throw new LibreHealthFHIRAnalyticsException("Error while reading data from files", e);
            }
        }
    }

    @Override
    public void loadAllData(JavaSparkContext sc, SparkSession sparkSession) {
        loadPatients(sc, sparkSession);
        loadObservations(sc, sparkSession);
        loadHealthcareServices(sc, sparkSession);
        loadAccounts(sc, sparkSession);
        loadDeviceRequests(sc, sparkSession);
        loadSupplyRequestss(sc, sparkSession);
        loadReferralRequests(sc, sparkSession);
        loadPractitioners(sc, sparkSession);
        loadMeasureReports(sc, sparkSession);
        loadCarePlans(sc, sparkSession);
        loadLocations(sc, sparkSession);
        loadSchedules(sc, sparkSession);
        loadEncounters(sc, sparkSession);
        loadMedications(sc, sparkSession);
        loadRiskAssessments(sc, sparkSession);
        loadEnrolementRequests(sc, sparkSession);
        loadMedicationRequests(sc, sparkSession);
        loadMedicationStatements(sc, sparkSession);
        loadMedicationDispenses(sc, sparkSession);
        loadSubstances(sc, sparkSession);
        loadImmunizationRecommendations(sc, sparkSession);
        loadSpecimens(sc, sparkSession);
        loadQuestionaires(sc, sparkSession);
        loadAdverseEvents(sc, sparkSession);
        loadEligibilityRequests(sc, sparkSession);
        loadContracts(sc, sparkSession);
        loadRelatedPersons(sc, sparkSession);
        loadImagingStudies(sc, sparkSession);
        loadPaymentNotices(sc, sparkSession);
        loadProcessRequests(sc, sparkSession);
        loadSubscriptions(sc, sparkSession);
        loadFamilyMemberHistories(sc, sparkSession);
        loadLibraries(sc, sparkSession);
        loadAllergyIntolerences(sc, sparkSession);
        loadNutritionOrders(sc, sparkSession);
        loadImagineManifests(sc, sparkSession);
        loadPersons(sc, sparkSession);
        loadImmunizations(sc, sparkSession);
        loadVisionPrescriptios(sc, sparkSession);
        loadAppointments(sc, sparkSession);
        loadDiagnosticReports(sc, sparkSession);
        loadConditions(sc, sparkSession);
        loadOrganizations(sc, sparkSession);
        loadProcedures(sc, sparkSession);
    }

    public void loadPatients(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Patient> patientRDD = javaFunctions(sc).cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                LibreHealthAnalyticConstants.PATIENT)
                .map((Function<CassandraRow, Patient>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    String patientSrt = cassandraRow.getString(LibreHealthAnalyticConstants.VALUE);
                    return parser.parseResource(Patient.class, patientSrt);
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Patient> peopleDFq = sparkSession.createDataset(patientRDD.rdd(), encoders.of(Patient.class));
        peopleDFq.createOrReplaceTempView(LibreHealthAnalyticConstants.PATIENT);
    }

    public void loadHealthcareServices(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<HealthcareService> healthCareServiceRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.HEALTHCARE_SERVICE)
                .map((Function<CassandraRow, HealthcareService>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    String healthCareServiceStr = cassandraRow.getString(LibreHealthAnalyticConstants.VALUE);
                    return parser.parseResource(HealthcareService.class, healthCareServiceStr);
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<HealthcareService> observationDfq = sparkSession.createDataset(healthCareServiceRDD.rdd(),
                encoders.of(HealthcareService.class));
        observationDfq.createOrReplaceTempView(LibreHealthAnalyticConstants.HEALTHCARE_SERVICE);
    }

    public void loadDeviceRequests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<DeviceRequest> deviceRequestRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.DEVICE_REQUEST)
                .map((Function<CassandraRow, DeviceRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(DeviceRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<DeviceRequest> deviceRequestDataset = sparkSession.createDataset(deviceRequestRDD.rdd(),
                encoders.of(DeviceRequest.class));
        deviceRequestDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.DEVICE_REQUEST);
    }


    public void loadSupplyRequestss(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<SupplyRequest> observationRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.SUPPLY_REQUEST)
                .map((Function<CassandraRow, SupplyRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(SupplyRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<SupplyRequest> supplyReq = sparkSession.createDataset(observationRDD.rdd(),
                encoders.of(SupplyRequest.class));
        supplyReq.createOrReplaceTempView(LibreHealthAnalyticConstants.SUPPLY_REQUEST);
    }

    public void loadReferralRequests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<ReferralRequest> referralRequestRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.REFERRAL_REQUEST)
                .map((Function<CassandraRow, ReferralRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(ReferralRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<ReferralRequest> observationDfq = sparkSession.createDataset(referralRequestRDD.rdd(),
                encoders.of(ReferralRequest.class));
        observationDfq.createOrReplaceTempView(LibreHealthAnalyticConstants.REFERRAL_REQUEST);
    }


    public void loadMedicationDispenses(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<MedicationDispense> medicationDispenseRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.MEDICATION_DISPENSE)
                .map((Function<CassandraRow, MedicationDispense>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
                    return parser.parseResource(MedicationDispense.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<MedicationDispense> dispenseDataset = sparkSession.createDataset(medicationDispenseRDD.rdd(),
                encoders.of(MedicationDispense.class));
        dispenseDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.MEDICATION_DISPENSE);
    }

    public void loadPractitioners(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Practitioner> practitionerJavaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.PRACTITIONER)
                .map((Function<CassandraRow, Practitioner>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Practitioner.class,
                                                cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Practitioner> practitionerDataset = sparkSession.createDataset(practitionerJavaRDD.rdd(),
                encoders.of(Practitioner.class));
        practitionerDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.PRACTITIONER);
    }

    public void loadMeasureReports(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<MeasureReport> measureReportRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.MEASURE_REPORT)
                .map((Function<CassandraRow, MeasureReport>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(MeasureReport.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<MeasureReport> reportDataset = sparkSession.createDataset(measureReportRDD.rdd(),
                encoders.of(MeasureReport.class));
        reportDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.MEASURE_REPORT);
    }

    public void loadCarePlans(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<CarePlan> objectJavaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.CAREPLAN)
                .map((Function<CassandraRow, CarePlan>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
                    return parser.parseResource(CarePlan.class,
                                                        cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<CarePlan> carePlanDataset = sparkSession.createDataset(objectJavaRDD.rdd(),
                encoders.of(CarePlan.class));
        carePlanDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.CAREPLAN);
    }


    public void loadAccounts(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Account> accountJavaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ACCOUNT)
                .map((Function<CassandraRow, Account>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
                    return parser.parseResource(Account.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Account> accountDataset = sparkSession.createDataset(accountJavaRDD.rdd(),
                encoders.of(Account.class));
        accountDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.ACCOUNT);
    }

    public void loadLocations(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Location> locationJavaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.LOCATION)
                .map((Function<CassandraRow, Location>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Location.class,
                                                    cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Location> locationDataset = sparkSession.createDataset(locationJavaRDD.rdd(),
                encoders.of(Location.class));
        locationDataset.createOrReplaceTempView(LibreHealthAnalyticConstants.LOCATION);
    }

    public void loadSchedules(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Schedule> scheduleJavaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.SCHEDULE)
                .map((Function<CassandraRow, Schedule>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Schedule.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Schedule> dataset = sparkSession.createDataset(scheduleJavaRDD.rdd(),
                encoders.of(Schedule.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.SCHEDULE);
    }

    public void loadEncounters(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Encounter> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ENCOUNTER)
                .map((Function<CassandraRow, Encounter>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Encounter.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Encounter> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Encounter.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.ENCOUNTER);
    }

    public void loadMedications(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Medication> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.MEDICATION)
                .map((Function<CassandraRow, Medication>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Medication.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Medication> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Medication.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.MEDICATION);
    }

    public void loadRiskAssessments(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<RiskAssessment> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.RISK_ASSESSMENT)
                .map((Function<CassandraRow, RiskAssessment>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(RiskAssessment.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<RiskAssessment> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(RiskAssessment.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.RISK_ASSESSMENT);
    }

    public void loadEnrolementRequests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<EnrollmentRequest> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ENROLLMENT_REQUEST)
                .map((Function<CassandraRow, EnrollmentRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(EnrollmentRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<EnrollmentRequest> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(EnrollmentRequest.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.RISK_ASSESSMENT);
    }

    public void loadSubstances(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Substance> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.SUBSTANCE)
                .map((Function<CassandraRow, Substance>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Substance.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Substance> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Substance.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.SUBSTANCE);
    }

    public void loadMedicationRequests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<MedicationRequest> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.MEDICATION_REQUEST)
                .map((Function<CassandraRow, MedicationRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(MedicationRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<MedicationRequest> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(MedicationRequest.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.MEDICATION_REQUEST);
    }

    public void loadSpecimens(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Specimen> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.SPECIMEN)
                .map((Function<CassandraRow, Specimen>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Specimen.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Specimen> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Specimen.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.SPECIMEN);
    }

    public void loadImmunizationRecommendations(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<ImmunizationRecommendation> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.IMMUNIZATION_RECOMMENDATION)
                .map((Function<CassandraRow, ImmunizationRecommendation>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(ImmunizationRecommendation.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<ImmunizationRecommendation> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(ImmunizationRecommendation.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.IMMUNIZATION_RECOMMENDATION);
    }

    public void loadEligibilityRequests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<EligibilityRequest> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ELIGIBILITY_REQUEST)
                .map((Function<CassandraRow, EligibilityRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(EligibilityRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<EligibilityRequest> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(EligibilityRequest.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.ELIGIBILITY_REQUEST);
    }

    public void loadQuestionaires(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Questionnaire> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.QUESTIONNAIRE)
                .map((Function<CassandraRow, Questionnaire>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Questionnaire.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Questionnaire> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Questionnaire.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.QUESTIONNAIRE);
    }

    public void loadImagineManifests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<ImagingManifest> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.IMAGING_MANIFEST)
                .map((Function<CassandraRow, ImagingManifest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(ImagingManifest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<ImagingManifest> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(ImagingManifest.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.IMAGING_MANIFEST);
    }

    public void loadAdverseEvents(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<AdverseEvent> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ADVERSE_EVENT)
                .map((Function<CassandraRow, AdverseEvent>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(AdverseEvent.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<AdverseEvent> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(AdverseEvent.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.ADVERSE_EVENT);
    }

    public void loadPersons(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Person> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.PERSON)
                .map((Function<CassandraRow, Person>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Person.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Person> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Person.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.PERSON);
    }

    public void loadImmunizations(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Immunization> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.IMMUNIZATION)
                .map((Function<CassandraRow, Immunization>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Immunization.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Immunization> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Immunization.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.IMMUNIZATION);
    }

    public void loadConditions(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Condition> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.CONDITION)
                .map((Function<CassandraRow, Condition>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Condition.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Condition> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Condition.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.CONDITION);
    }

    public void loadObservations(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Observation> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.OBSERVATION)
                .map((Function<CassandraRow, Observation>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Observation.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Observation> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Observation.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.OBSERVATION);
    }

    public void loadAppointments(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Appointment> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.APPOINTMENT)
                .map((Function<CassandraRow, Appointment>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Appointment.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Appointment> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Appointment.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.APPOINTMENT);
    }

    public void loadOrganizations(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Organization> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ORGANIZATION)
                .map((Function<CassandraRow, Organization>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Organization.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Organization> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Organization.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.ORGANIZATION);
    }

    public void loadDiagnosticReports(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<DiagnosticReport> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.DIAGNOSTICREPORT)
                .map((Function<CassandraRow, DiagnosticReport>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(DiagnosticReport.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<DiagnosticReport> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(DiagnosticReport.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.DIAGNOSTICREPORT);
        dataset.printSchema();
    }

    public void loadProcedures(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Procedure> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.PROCEDURE)
                .map((Function<CassandraRow, Procedure>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Procedure.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Procedure> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Procedure.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.PROCEDURE);
    }

    public void loadNutritionOrders(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<NutritionOrder> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.NUTRITION_ORDER)
                .map((Function<CassandraRow, NutritionOrder>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(NutritionOrder.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<NutritionOrder> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(NutritionOrder.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.NUTRITION_ORDER);
    }

    public void loadAllergyIntolerences(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<AllergyIntolerance> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.ALLERGY_INTOLERANCE)
                .map((Function<CassandraRow, AllergyIntolerance>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(AllergyIntolerance.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<AllergyIntolerance> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(AllergyIntolerance.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.ALLERGY_INTOLERANCE);
    }

    public void loadMedicationStatements(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<MedicationStatement> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.MEDICATION_STATEMENT)
                .map((Function<CassandraRow, MedicationStatement>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(MedicationStatement.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<MedicationStatement> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(MedicationStatement.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.MEDICATION_STATEMENT);
    }

    public void loadVisionPrescriptios(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<VisionPrescription> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.VISION_PRESCRIPTION)
                .map((Function<CassandraRow, VisionPrescription>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(VisionPrescription.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<VisionPrescription> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(VisionPrescription.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.VISION_PRESCRIPTION);
    }

    public void loadLibraries(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Library> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.LIBRARY)
                .map((Function<CassandraRow, Library>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Library.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Library> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Library.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.LIBRARY);
    }

    public void loadFamilyMemberHistories(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<FamilyMemberHistory> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.FAMILYMEMBERHISTORY)
                .map((Function<CassandraRow, FamilyMemberHistory>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(FamilyMemberHistory.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<FamilyMemberHistory> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(FamilyMemberHistory.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.FAMILYMEMBERHISTORY);
    }

    public void loadSubscriptions(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Subscription> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.SUBSCRIPTION)
                .map((Function<CassandraRow, Subscription>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Subscription.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Subscription> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Subscription.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.SUBSCRIPTION);
    }

    public void loadProcessRequests(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<ProcessRequest> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.PROCESS_REQUEST)
                .map((Function<CassandraRow, ProcessRequest>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(ProcessRequest.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<ProcessRequest> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(ProcessRequest.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.PROCESS_REQUEST);
    }

    public void loadPaymentNotices(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<PaymentNotice> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.PAYMENTNOTICE)
                .map((Function<CassandraRow, PaymentNotice>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(PaymentNotice.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<PaymentNotice> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(PaymentNotice.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.PAYMENTNOTICE);
    }

    public void loadImagingStudies(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<ImagingStudy> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.IMAGINGSTUDY)
                .map((Function<CassandraRow, ImagingStudy>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(ImagingStudy.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<ImagingStudy> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(ImagingStudy.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.IMAGINGSTUDY);
    }

    public void loadRelatedPersons(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<RelatedPerson> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.RELATED_PERSON)
                .map((Function<CassandraRow, RelatedPerson>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(RelatedPerson.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<RelatedPerson> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(RelatedPerson.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.RELATED_PERSON);
    }

    public void loadContracts(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Contract> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.CONTRACT)
                .map((Function<CassandraRow, Contract>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Contract.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Contract> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Contract.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.CONTRACT);
    }

    public void loadDevices(JavaSparkContext sc, SparkSession sparkSession) {
        JavaRDD<Device> javaRDD = javaFunctions(sc).
                cassandraTable(LibreHealthAnalyticConstants.LIBRE_HEALTH_KEYSPACE,
                        LibreHealthAnalyticConstants.DEVICE)
                .map((Function<CassandraRow, Device>) cassandraRow -> {
                    FhirContext fhirCtx = FhirContext.forDstu3();
                    IParser parser = fhirCtx.newJsonParser().setPrettyPrint(false);
                    return parser.parseResource(Device.class,
                            cassandraRow.getString(LibreHealthAnalyticConstants.VALUE));
                });
        FhirEncoders encoders = FhirEncoders.forStu3().getOrCreate();
        Dataset<Device> dataset = sparkSession.createDataset(javaRDD.rdd(),
                encoders.of(Device.class));
        dataset.createOrReplaceTempView(LibreHealthAnalyticConstants.DEVICE);
    }

    @Override
    public void insertData(Session session, String resource) {
        FhirContext fhirCtx = FhirContext.forDstu3();
        IParser parser = fhirCtx.newJsonParser().setPrettyPrint(true);
        String resourceType;
        String cqlTableTemplate = LibreHealthAnalyticConstants.CREATE_TABLE_TEMPLATED_CQL;
        String cqlInsertTemplate = LibreHealthAnalyticConstants.INSERT_VALUES_TEMPLATED_CQL;
        Resource fhirResource;
        Bundle bundle;
        IBaseResource baseResource = parser.parseResource(resource);
        IIdType iIdType = baseResource.getIdElement();
        resourceType = iIdType.getResourceType();
        if (!(baseResource instanceof Bundle) && baseResource instanceof Resource) {
            if (StringUtils.isEmpty(resourceType) || StringUtils.isEmpty(iIdType.getIdPart())) {
                return;
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
    }
}
