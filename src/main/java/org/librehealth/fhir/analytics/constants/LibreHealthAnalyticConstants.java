package org.librehealth.fhir.analytics.constants;

import java.io.File;

public class LibreHealthAnalyticConstants {
    String query = "SELECT * " +
            "FROM patient inner join observation where observation.subject.reference == patient.id";
    public static final String DATA_PATH = "data" + File.separator + "resources" + File.separator + "json";
    public static final String LIBRE_HEALTH_KEYSPACE = "librehealth";
    public static final String VALUE = "value";
    public static final String CREATE_LIBRE_HEALTH_KEYSPACE = "CREATE KEYSPACE IF NOT EXISTS librehealth WITH " +
            "REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1}";
    public static final String CREATE_TABLE_TEMPLATED_CQL = "CREATE TABLE IF NOT EXISTS librehealth.%s " +
            "(id varchar, value text, PRIMARY KEY(id))";
    public static final String INSERT_VALUES_TEMPLATED_CQL = "INSERT INTO librehealth.%s (ID, VALUE) values " +
            "(?, ?) IF NOT EXISTS";
    public static final String PATIENT = "patient";
    public static final String OBSERVATION = "observation";

    public static final String STRUCTURE_DEFINITION = "structuredefinition";
    public static final String HEALTHCARE_SERVICE = "healthcareservice";
    public static final String DEVICE_REQUEST = "devicerequest";
    public static final String DEVICE_COMPONENT = "devicecomponent";
    public static final String ELIGIBILITY_RESPONSE = "eligibilityresponse";
    public static final String SUPPLY_REQUEST = "supplyrequest";
    public static final String REFERRAL_REQUEST = "referralrequest";
    public static final String OPERATION_DEFINITION = "operationdefinition";
    public static final String MEDICATION_DISPENSE = "medicationdispense";
    public static final String MEASURE_REPORT = "measurereport";
    public static final String APPOINTMENT_RESPONSE = "appointmentresponse";
    public static final String GOAL = "goal";
    public static final String PRACTITIONER = "practitioner";
    public static final String CAREPLAN = "careplan";
    public static final String CONTRACT = "contract";
    public static final String COMPARTMENT_DEFINITION = "compartmentdefinition";
    public static final String RELATED_PERSON = "relatedperson";
    public static final String RESEARCH_SUBJECT = "researchsubject";
    public static final String BINARY = "binary";
    public static final String SERVICEDEFINITION = "servicedefinition";
    public static final String EXPLANATIONOFBENEFIT = "explanationofbenefit";
    public static final String DEVICE = "device";
    public static final String DETECTEDISSUE = "detectedissue";
    public static final String IMAGINGSTUDY = "imagingstudy";
    public static final String PROCESS_RESPONSE = "processresponse";
    public static final String CLAIM_RESPONSE = "claimresponse";
    public static final String FLAG = "flag";
    public static final String PAYMENTNOTICE = "paymentnotice";
    public static final String PROCESS_REQUEST = "processrequest";
    public static final String GUIDANCE_RESPONSE = "guidanceresponse";
    public static final String EXPANSION_PROFILE = "expansionprofile";
    public static final String LINKAGE = "linkage";
    public static final String SUBSCRIPTION = "subscription";
    public static final String TEST_REPORT = "testreport";
    public static final String REQUEST_GROUP = "requestgroup";
    public static final String FAMILYMEMBERHISTORY = "familymemberhistory";
    public static final String PLAN_DEFINITION = "plandefinition";
    public static final String SUPPLY_DELIVERY = "supplydelivery";
    public static final String DEVICE_USE_STATEMENT = "deviceusestatement";
    public static final String ENDPOINT = "endpoint";
    public static final String LIBRARY = "library";
    public static final String VISION_PRESCRIPTION = "visionprescription";
    public static final String COMPOSITION = "composition";
    public static final String CHARGE_ITEM = "chargeitem";
    public static final String MEDICATION_STATEMENT = "medicationstatement";
    public static final String BODYSITE = "bodysite";
    public static final String CONCEPTMAP = "conceptmap";
    public static final String ALLERGY_INTOLERANCE = "allergyintolerance";
    public static final String NAMING_SYSTEM = "namingsystem";
    public static final String NUTRITION_ORDER = "nutritionorder";
    public static final String PROCEDURE = "procedure";
    public static final String RESEARCH_STUDY = "researchstudy";
    public static final String MESSAGE_DEFINITION = "messagedefinition";
    public static final String CARE_TEAM = "careteam";
    public static final String DIAGNOSTICREPORT = "diagnosticreport";
    public static final String DATA_ELEMENT = "dataelement";
    public static final String ORGANIZATION = "organization";
    public static final String TESTSCRIPT = "testscript";
    public static final String MESSAGEHEADER = "messageheader";
    public static final String ACTIVITYDEFINITION = "activitydefinition";
    public static final String DOCUMENT_REFERENCE = "documentreference";
    public static final String APPOINTMENT = "appointment";
    public static final String CLINICAL_IMPRESSION = "clinicalimpression";
    public static final String MEDICATIONADMINISTRATION = "medicationadministration";
    public static final String IMPLEMENTATION_GUIDE = "implementationguide";
    public static final String CLAIM = "claim";
    public static final String SEARCH_PARAMETER = "searchparameter";
    public static final String COMMUNICATION = "communication";
    public static final String GROUP = "group";
    public static final String VALUESET = "valueset";
    public static final String PROCEDURE_REQUEST = "procedurerequest";
    public static final String CODE_SYSTEM = "codesystem";
    public static final String AUDIT_EVENT = "auditevent";
    public static final String QUESTIONNAIRE_RESPONSE = "questionnaireresponse";
    public static final String OPERATION_OUTCOME = "operationoutcome";
    public static final String CONSENT = "consent";
    public static final String LIST = "list";
    public static final String COMMUNICATION_REQUEST = "communicationrequest";
    public static final String SEQUENCE = "sequence";
    public static final String CONDITION = "condition";
    public static final String MEASURE = "measure";
    public static final String TASK = "task";
    public static final String DOCUMENT_MANIFEST = "documentmanifest";
    public static final String IMMUNIZATION = "immunization";
    public static final String PERSON = "person";
    public static final String ADVERSE_EVENT = "adverseevent";
    public static final String BASIC = "basic";
    public static final String DEVICE_METRIC = "devicemetric";
    public static final String IMAGING_MANIFEST = "imagingmanifest";
    public static final String CAPABILITY_STATEMENT = "capabilitystatement";
    public static final String EPISODE_OF_CARE = "episodeofcare";
    public static final String QUESTIONNAIRE = "questionnaire";
    public static final String ELIGIBILITY_REQUEST = "eligibilityrequest";
    public static final String MEDIA = "media";
    public static final String SLOT = "slot";
    public static final String IMMUNIZATION_RECOMMENDATION = "immunizationrecommendation";
    public static final String PROVENANCE = "provenance";
    public static final String PRACTITIONER_ROLE = "practitionerrole";
    public static final String SPECIMEN = "specimen";
    public static final String COVERAGE = "coverage";
    public static final String ENROLLMENT_RESPONSE = "enrollmentresponse";
    public static final String STRUCTURE_MAP = "structuremap";
    public static final String MEDICATION_REQUEST = "medicationrequest";
    public static final String PAYMENT_RECONCILIATION = "paymentreconciliation";
    public static final String SUBSTANCE = "substance";
    public static final String ENROLLMENT_REQUEST = "enrollmentrequest";
    public static final String RISK_ASSESSMENT = "riskassessment";
    public static final String MEDICATION = "medication";
    public static final String ENCOUNTER = "encounter";
    public static final String SCHEDULE = "schedule";
    public static final String GRAPH_DEFINITION  = "graphdefinition";
    public static final String LOCATION = "location";
    public static final String ACCOUNT = "account";
}
