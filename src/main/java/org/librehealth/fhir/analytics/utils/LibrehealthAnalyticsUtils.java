package org.librehealth.fhir.analytics.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.librehealth.fhir.analytics.cassandra.CassandraDataService;
import org.librehealth.fhir.analytics.cassandra.CassandraDataServiceImpl;
import org.librehealth.fhir.analytics.constants.LibreHealthAnalyticConstants;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.model.SparkSQLOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LibrehealthAnalyticsUtils {

    public static String readFileAsString(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        InputStreamReader inputStreamREader = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStreamREader = new InputStreamReader(is, StandardCharsets.UTF_8);
            br = new BufferedReader(inputStreamREader);
            String content = br.readLine();
            if (content == null) {
                return sb.toString();
            }

            sb.append(content);

            while ((content = br.readLine()) != null) {
                sb.append('\n').append(content);
            }
        } finally {
            if (inputStreamREader != null) {
                try {
                    inputStreamREader.close();
                } catch (IOException ignore) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignore) {
                }
            }
        }
        return sb.toString();
    }


    public static String buildResponseJsonForSparkSQL(List<Row> rows, String[] columns) throws JsonProcessingException {
        SparkSQLOutput output = new SparkSQLOutput();
        output.setColumns(columns);
        int columnLength = columns.length;
        String[] data;
        Object columnContent;
        List<String[]> sparkSQLRows = new ArrayList<>();
        int j = 0;
        for (Row row : rows) {
            data = new String[columnLength];
            for (int i = 0; i < columnLength; i++) {
                columnContent = row.get(i);
                if (columnContent != null) {
                    data[i] = columnContent.toString();
                } else {
                    data[i] = Strings.EMPTY;
                }
            }
            sparkSQLRows.add(data);
        }
        output.setData(sparkSQLRows);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(output);
        return jsonInString;
    }

    public static String executeSql(String query, SparkSession sparkSession)
            throws JsonProcessingException, LibreHealthFHIRAnalyticsException {
        Dataset<Row> obsDF = sparkSession.sql(query);
        String out = LibrehealthAnalyticsUtils.buildResponseJsonForSparkSQL(obsDF.collectAsList(), obsDF.columns());
        return out;
    }

    public static void loadDataByViews(String[] views, JavaSparkContext sc, SparkSession sparkSession) {
        for (String view : views) {
            loadDataByView(view, sc, sparkSession);
        }
    }

    public static void loadDataByView(String view, JavaSparkContext sc, SparkSession sparkSession) {
        CassandraDataService dataService = CassandraDataServiceImpl.getInstance();

        if (LibreHealthAnalyticConstants.STRUCTURE_DEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.HEALTHCARE_SERVICE.equalsIgnoreCase(view)) {
            dataService.loadHealthcareServices(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.DEVICE_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadDeviceRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.DEVICE_COMPONENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.ELIGIBILITY_RESPONSE.equalsIgnoreCase(view)) {
            dataService.loadEligibilityRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.SUPPLY_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadSupplyRequestss(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.REFERRAL_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadReferralRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.OPERATION_DEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.MEDICATION_DISPENSE.equalsIgnoreCase(view)) {
            dataService.loadMedicationDispenses(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.MEASURE_REPORT.equalsIgnoreCase(view)) {
            dataService.loadMeasureReports(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.APPOINTMENT_RESPONSE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.GOAL.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.PRACTITIONER.equalsIgnoreCase(view)) {
            dataService.loadPractitioners(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.CAREPLAN.equalsIgnoreCase(view)) {
            dataService.loadCarePlans(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.CONTRACT.equalsIgnoreCase(view)) {
            dataService.loadContracts(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.COMPARTMENT_DEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.RELATED_PERSON.equalsIgnoreCase(view)) {
            dataService.loadRelatedPersons(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.RESEARCH_SUBJECT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.BINARY.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SERVICEDEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.EXPLANATIONOFBENEFIT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.DEVICE.equalsIgnoreCase(view)) {
            dataService.loadDevices(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.DETECTEDISSUE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.IMAGINGSTUDY.equalsIgnoreCase(view)) {
            dataService.loadDevices(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PROCESS_RESPONSE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CLAIM_RESPONSE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.FLAG.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.PAYMENTNOTICE.equalsIgnoreCase(view)) {
            dataService.loadPaymentNotices(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PROCESS_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadProcessRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.GUIDANCE_RESPONSE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.EXPANSION_PROFILE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.LINKAGE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SUBSCRIPTION.equalsIgnoreCase(view)) {
            dataService.loadSubscriptions(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.TEST_REPORT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.REQUEST_GROUP.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.FAMILYMEMBERHISTORY.equalsIgnoreCase(view)) {
            dataService.loadFamilyMemberHistories(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PLAN_DEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SUPPLY_DELIVERY.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.DEVICE_USE_STATEMENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.ENDPOINT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.LIBRARY.equalsIgnoreCase(view)) {
            dataService.loadLibraries(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.VISION_PRESCRIPTION.equalsIgnoreCase(view)) {
            dataService.loadVisionPrescriptios(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.COMPOSITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CHARGE_ITEM.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.MEDICATION_STATEMENT.equalsIgnoreCase(view)) {
            dataService.loadMedicationStatements(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.BODYSITE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CONCEPTMAP.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.ALLERGY_INTOLERANCE.equalsIgnoreCase(view)) {
            dataService.loadAllergyIntolerences(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.NAMING_SYSTEM.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.NUTRITION_ORDER.equalsIgnoreCase(view)) {
            dataService.loadNutritionOrders(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PROCEDURE.equalsIgnoreCase(view)) {
            dataService.loadProcedures(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.RESEARCH_STUDY.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.MESSAGE_DEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CARE_TEAM.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.DIAGNOSTICREPORT.equalsIgnoreCase(view)) {
            dataService.loadDiagnosticReports(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.DATA_ELEMENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.ORGANIZATION.equalsIgnoreCase(view)) {
            dataService.loadOrganizations(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.TESTSCRIPT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.MESSAGEHEADER.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.ACTIVITYDEFINITION.equalsIgnoreCase(view)) {
            dataService.loadDiagnosticReports(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.DOCUMENT_REFERENCE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.APPOINTMENT.equalsIgnoreCase(view)) {
            dataService.loadAppointments(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.CLINICAL_IMPRESSION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.MEDICATIONADMINISTRATION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.IMPLEMENTATION_GUIDE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CLAIM.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SEARCH_PARAMETER.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.COMMUNICATION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.GROUP.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.VALUESET.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.PROCEDURE_REQUEST.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CODE_SYSTEM.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.AUDIT_EVENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.QUESTIONNAIRE_RESPONSE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.OPERATION_OUTCOME.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CONSENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.LIST.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.COMMUNICATION_REQUEST.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SEQUENCE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.CONDITION.equalsIgnoreCase(view)) {
            dataService.loadConditions(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.MEASURE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.TASK.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.DOCUMENT_MANIFEST.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.IMMUNIZATION.equalsIgnoreCase(view)) {
            dataService.loadImmunizations(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PERSON.equalsIgnoreCase(view)) {
            dataService.loadPersons(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.ADVERSE_EVENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.BASIC.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.DEVICE_METRIC.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.IMAGING_MANIFEST.equalsIgnoreCase(view)) {
            dataService.loadImagineManifests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.CAPABILITY_STATEMENT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.EPISODE_OF_CARE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.QUESTIONNAIRE.equalsIgnoreCase(view)) {
            dataService.loadQuestionaires(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.ELIGIBILITY_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadEligibilityRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.MEDIA.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SLOT.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.IMMUNIZATION_RECOMMENDATION.equalsIgnoreCase(view)) {
            dataService.loadImmunizationRecommendations(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PROVENANCE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.PRACTITIONER_ROLE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SPECIMEN.equalsIgnoreCase(view)) {
            dataService.loadSpecimens(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.COVERAGE.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.ENROLLMENT_RESPONSE.equalsIgnoreCase(view)) {
            dataService.loadEnrolementRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.STRUCTURE_MAP.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.MEDICATION_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadMedicationRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PAYMENT_RECONCILIATION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.SUBSTANCE.equalsIgnoreCase(view)) {
            dataService.loadSubstances(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.ENROLLMENT_REQUEST.equalsIgnoreCase(view)) {
            dataService.loadEnrolementRequests(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.RISK_ASSESSMENT.equalsIgnoreCase(view)) {
            dataService.loadRiskAssessments(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.MEDICATION.equalsIgnoreCase(view)) {
            dataService.loadMedications(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.ENCOUNTER.equalsIgnoreCase(view)) {
            dataService.loadEncounters(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.SCHEDULE.equalsIgnoreCase(view)) {
            dataService.loadSchedules(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.GRAPH_DEFINITION.equalsIgnoreCase(view)) {
            //TODO implement
        } else if (LibreHealthAnalyticConstants.LOCATION.equalsIgnoreCase(view)) {
            dataService.loadLocations(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.ACCOUNT.equalsIgnoreCase(view)) {
            dataService.loadAccounts(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.PATIENT.equalsIgnoreCase(view)) {
            dataService.loadPatients(sc, sparkSession);
        } else if (LibreHealthAnalyticConstants.OBSERVATION.equalsIgnoreCase(view)) {
            dataService.loadObservations(sc, sparkSession);
        }
    }

    public static List<String> containsViews(String sql) {
        List<String> views = new ArrayList<>();
        if (StringUtils.isEmpty(sql)) {
            return views;
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.STRUCTURE_DEFINITION)) {
            views.add(LibreHealthAnalyticConstants.STRUCTURE_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.HEALTHCARE_SERVICE)) {
            views.add(LibreHealthAnalyticConstants.HEALTHCARE_SERVICE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DEVICE_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.DEVICE_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DEVICE_COMPONENT)) {
            views.add(LibreHealthAnalyticConstants.DEVICE_COMPONENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ELIGIBILITY_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.ELIGIBILITY_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SUPPLY_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.SUPPLY_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.REFERRAL_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.REFERRAL_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.OPERATION_DEFINITION)) {
            views.add(LibreHealthAnalyticConstants.OPERATION_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEDICATION_DISPENSE)) {
            views.add(LibreHealthAnalyticConstants.MEDICATION_DISPENSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEASURE_REPORT)) {
            views.add(LibreHealthAnalyticConstants.MEASURE_REPORT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.APPOINTMENT_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.APPOINTMENT_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.GOAL)) {
            views.add(LibreHealthAnalyticConstants.GOAL);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PRACTITIONER)) {
            views.add(LibreHealthAnalyticConstants.PRACTITIONER);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CAREPLAN)) {
            views.add(LibreHealthAnalyticConstants.CAREPLAN);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CONTRACT)) {
            views.add(LibreHealthAnalyticConstants.CONTRACT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.COMPARTMENT_DEFINITION)) {
            views.add(LibreHealthAnalyticConstants.COMPARTMENT_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.RELATED_PERSON)) {
            views.add(LibreHealthAnalyticConstants.RELATED_PERSON);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.RESEARCH_SUBJECT)) {
            views.add(LibreHealthAnalyticConstants.RESEARCH_SUBJECT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.BINARY)) {
            views.add(LibreHealthAnalyticConstants.BINARY);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SERVICEDEFINITION)) {
            views.add(LibreHealthAnalyticConstants.SERVICEDEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.EXPLANATIONOFBENEFIT)) {
            views.add(LibreHealthAnalyticConstants.EXPLANATIONOFBENEFIT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DEVICE)) {
            views.add(LibreHealthAnalyticConstants.DEVICE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DETECTEDISSUE)) {
            views.add(LibreHealthAnalyticConstants.DETECTEDISSUE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.IMAGINGSTUDY)) {
            views.add(LibreHealthAnalyticConstants.IMAGINGSTUDY);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PROCESS_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.PROCESS_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CLAIM_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.CLAIM_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.FLAG)) {
            views.add(LibreHealthAnalyticConstants.FLAG);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PAYMENTNOTICE)) {
            views.add(LibreHealthAnalyticConstants.PAYMENTNOTICE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PROCESS_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.PROCESS_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.GUIDANCE_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.GUIDANCE_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.EXPANSION_PROFILE)) {
            views.add(LibreHealthAnalyticConstants.EXPANSION_PROFILE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.LINKAGE)) {
            views.add(LibreHealthAnalyticConstants.LINKAGE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SUBSCRIPTION)) {
            views.add(LibreHealthAnalyticConstants.SUBSCRIPTION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.TEST_REPORT)) {
            views.add(LibreHealthAnalyticConstants.TEST_REPORT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.REQUEST_GROUP)) {
            views.add(LibreHealthAnalyticConstants.REQUEST_GROUP);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.FAMILYMEMBERHISTORY)) {
            views.add(LibreHealthAnalyticConstants.FAMILYMEMBERHISTORY);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PLAN_DEFINITION)) {
            views.add(LibreHealthAnalyticConstants.PLAN_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SUPPLY_DELIVERY)) {
            views.add(LibreHealthAnalyticConstants.SUPPLY_DELIVERY);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DEVICE_USE_STATEMENT)) {
            views.add(LibreHealthAnalyticConstants.DEVICE_USE_STATEMENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ENDPOINT)) {
            views.add(LibreHealthAnalyticConstants.ENDPOINT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.LIBRARY)) {
            views.add(LibreHealthAnalyticConstants.LIBRARY);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.VISION_PRESCRIPTION)) {
            views.add(LibreHealthAnalyticConstants.VISION_PRESCRIPTION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.COMPOSITION)) {
            views.add(LibreHealthAnalyticConstants.COMPOSITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.COMPOSITION)) {
            views.add(LibreHealthAnalyticConstants.COMPOSITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEDICATION_STATEMENT)) {
            views.add(LibreHealthAnalyticConstants.MEDICATION_STATEMENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.BODYSITE)) {
            views.add(LibreHealthAnalyticConstants.BODYSITE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CONCEPTMAP)) {
            views.add(LibreHealthAnalyticConstants.CONCEPTMAP);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ALLERGY_INTOLERANCE)) {
            views.add(LibreHealthAnalyticConstants.ALLERGY_INTOLERANCE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.NAMING_SYSTEM)) {
            views.add(LibreHealthAnalyticConstants.NAMING_SYSTEM);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.NUTRITION_ORDER)) {
            views.add(LibreHealthAnalyticConstants.NUTRITION_ORDER);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PROCEDURE)) {
            views.add(LibreHealthAnalyticConstants.PROCEDURE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.RESEARCH_STUDY)) {
            views.add(LibreHealthAnalyticConstants.RESEARCH_STUDY);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MESSAGE_DEFINITION)) {
            views.add(LibreHealthAnalyticConstants.MESSAGE_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CARE_TEAM)) {
            views.add(LibreHealthAnalyticConstants.CARE_TEAM);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DIAGNOSTICREPORT)) {
            views.add(LibreHealthAnalyticConstants.DIAGNOSTICREPORT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DATA_ELEMENT)) {
            views.add(LibreHealthAnalyticConstants.DATA_ELEMENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ORGANIZATION)) {
            views.add(LibreHealthAnalyticConstants.ORGANIZATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.TESTSCRIPT)) {
            views.add(LibreHealthAnalyticConstants.TESTSCRIPT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MESSAGEHEADER)) {
            views.add(LibreHealthAnalyticConstants.MESSAGEHEADER);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ACTIVITYDEFINITION)) {
            views.add(LibreHealthAnalyticConstants.ACTIVITYDEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DOCUMENT_REFERENCE)) {
            views.add(LibreHealthAnalyticConstants.DOCUMENT_REFERENCE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.APPOINTMENT)) {
            views.add(LibreHealthAnalyticConstants.APPOINTMENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CLINICAL_IMPRESSION)) {
            views.add(LibreHealthAnalyticConstants.CLINICAL_IMPRESSION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEDICATIONADMINISTRATION)) {
            views.add(LibreHealthAnalyticConstants.MEDICATIONADMINISTRATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.IMPLEMENTATION_GUIDE)) {
            views.add(LibreHealthAnalyticConstants.IMPLEMENTATION_GUIDE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CLAIM)) {
            views.add(LibreHealthAnalyticConstants.CLAIM);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SEARCH_PARAMETER)) {
            views.add(LibreHealthAnalyticConstants.SEARCH_PARAMETER);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.COMMUNICATION)) {
            views.add(LibreHealthAnalyticConstants.COMMUNICATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.GROUP)) {
            views.add(LibreHealthAnalyticConstants.GROUP);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.VALUESET)) {
            views.add(LibreHealthAnalyticConstants.VALUESET);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PROCEDURE_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.PROCEDURE_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CODE_SYSTEM)) {
            views.add(LibreHealthAnalyticConstants.CODE_SYSTEM);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.AUDIT_EVENT)) {
            views.add(LibreHealthAnalyticConstants.AUDIT_EVENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.QUESTIONNAIRE_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.QUESTIONNAIRE_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.OPERATION_OUTCOME)) {
            views.add(LibreHealthAnalyticConstants.OPERATION_OUTCOME);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CONSENT)) {
            views.add(LibreHealthAnalyticConstants.CONSENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.LIST)) {
            views.add(LibreHealthAnalyticConstants.LIST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.COMMUNICATION_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.COMMUNICATION_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SEQUENCE)) {
            views.add(LibreHealthAnalyticConstants.SEQUENCE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CONDITION)) {
            views.add(LibreHealthAnalyticConstants.CONDITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEASURE)) {
            views.add(LibreHealthAnalyticConstants.MEASURE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.TASK)) {
            views.add(LibreHealthAnalyticConstants.TASK);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DOCUMENT_MANIFEST)) {
            views.add(LibreHealthAnalyticConstants.DOCUMENT_MANIFEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.IMMUNIZATION)) {
            views.add(LibreHealthAnalyticConstants.IMMUNIZATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PERSON)) {
            views.add(LibreHealthAnalyticConstants.PERSON);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ADVERSE_EVENT)) {
            views.add(LibreHealthAnalyticConstants.ADVERSE_EVENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.BASIC)) {
            views.add(LibreHealthAnalyticConstants.BASIC);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.DEVICE_METRIC)) {
            views.add(LibreHealthAnalyticConstants.DEVICE_METRIC);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.IMAGING_MANIFEST)) {
            views.add(LibreHealthAnalyticConstants.IMAGING_MANIFEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.CAPABILITY_STATEMENT)) {
            views.add(LibreHealthAnalyticConstants.CAPABILITY_STATEMENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.EPISODE_OF_CARE)) {
            views.add(LibreHealthAnalyticConstants.EPISODE_OF_CARE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.QUESTIONNAIRE)) {
            views.add(LibreHealthAnalyticConstants.QUESTIONNAIRE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ELIGIBILITY_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.ELIGIBILITY_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEDIA)) {
            views.add(LibreHealthAnalyticConstants.MEDIA);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SLOT)) {
            views.add(LibreHealthAnalyticConstants.SLOT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.IMMUNIZATION_RECOMMENDATION)) {
            views.add(LibreHealthAnalyticConstants.IMMUNIZATION_RECOMMENDATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PROVENANCE)) {
            views.add(LibreHealthAnalyticConstants.PROVENANCE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PRACTITIONER_ROLE)) {
            views.add(LibreHealthAnalyticConstants.PRACTITIONER_ROLE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SPECIMEN)) {
            views.add(LibreHealthAnalyticConstants.SPECIMEN);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.COVERAGE)) {
            views.add(LibreHealthAnalyticConstants.COVERAGE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ENROLLMENT_RESPONSE)) {
            views.add(LibreHealthAnalyticConstants.ENROLLMENT_RESPONSE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.STRUCTURE_MAP)) {
            views.add(LibreHealthAnalyticConstants.STRUCTURE_MAP);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEDICATION_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.MEDICATION_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PAYMENT_RECONCILIATION)) {
            views.add(LibreHealthAnalyticConstants.PAYMENT_RECONCILIATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SUBSTANCE)) {
            views.add(LibreHealthAnalyticConstants.SUBSTANCE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ENROLLMENT_REQUEST)) {
            views.add(LibreHealthAnalyticConstants.ENROLLMENT_REQUEST);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.RISK_ASSESSMENT)) {
            views.add(LibreHealthAnalyticConstants.RISK_ASSESSMENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.MEDICATION)) {
            views.add(LibreHealthAnalyticConstants.STRUCTURE_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ENCOUNTER)) {
            views.add(LibreHealthAnalyticConstants.ENCOUNTER);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.SCHEDULE)) {
            views.add(LibreHealthAnalyticConstants.SCHEDULE);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.GRAPH_DEFINITION)) {
            views.add(LibreHealthAnalyticConstants.GRAPH_DEFINITION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.LOCATION)) {
            views.add(LibreHealthAnalyticConstants.LOCATION);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.ACCOUNT)) {
            views.add(LibreHealthAnalyticConstants.ACCOUNT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.PATIENT)) {
            views.add(LibreHealthAnalyticConstants.PATIENT);
        }

        if (sql.toLowerCase().contains(LibreHealthAnalyticConstants.OBSERVATION)) {
            views.add(LibreHealthAnalyticConstants.OBSERVATION);
        }
        return views;
    }
}
