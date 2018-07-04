package org.librehealth.fhir.analytics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.librehealth.fhir.analytics.LibreHealthFHIRAnalyticsExecutionManager;
import org.librehealth.fhir.analytics.builder.DiagnosticReportSearchFilter;
import org.librehealth.fhir.analytics.builder.EncounterSearchFilter;
import org.librehealth.fhir.analytics.builder.MedicationRequestSearchFilter;
import org.librehealth.fhir.analytics.builder.ObservationSearchFilter;
import org.librehealth.fhir.analytics.builder.PatientAttributeSearchFilter;
import org.librehealth.fhir.analytics.builder.SparkQueryBuilder;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.model.SearchObj;
import org.librehealth.fhir.analytics.model.SparkSQLQuery;
import org.librehealth.fhir.analytics.utils.LibrehealthAnalyticsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
public class LibreHealthFHIRAnalyticController {
    private static final Logger logger = LoggerFactory.getLogger(LibreHealthFHIRAnalyticController.class);

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        return "home";
    }

    @RequestMapping(value = "/sql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> executeSQL(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";
        try {
            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            List<String> views = LibrehealthAnalyticsUtils.containsViews(queryDetails.getQuery());
            LibrehealthAnalyticsUtils.loadDataByViews(views.toArray(new String[views.size()]),
                    manager.getJavaSparkContext(), manager.getSparkSession());
            data = LibrehealthAnalyticsUtils.executeSql(queryDetails.getQuery(), manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            logger.error("Error while executing spark SQL", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing JSON", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/patient-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPatient(@RequestBody SearchObj searchObj) {
        String data = "";
        PatientAttributeSearchFilter patientAttributeSearchFilter = new PatientAttributeSearchFilter();
        patientAttributeSearchFilter.setRangeSearchFieldList(searchObj.getRangeFields());
        patientAttributeSearchFilter.setSearchFields(searchObj.getFields());
        patientAttributeSearchFilter.setTable(searchObj.getTableName());
        try {
            patientAttributeSearchFilter.processFields();

            SparkQueryBuilder queryBuilder = new SparkQueryBuilder();
            String sparkSQl = queryBuilder.setTableName(patientAttributeSearchFilter.getTable()).
                    setFields(patientAttributeSearchFilter.getSearchFields())
                    .setRangeFields(patientAttributeSearchFilter.getRangeSearchFieldList()).build();

            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            List<String> views = LibrehealthAnalyticsUtils.containsViews(sparkSQl);
            LibrehealthAnalyticsUtils.loadDataByViews(views.toArray(new String[views.size()]),
                    manager.getJavaSparkContext(), manager.getSparkSession());
            data = LibrehealthAnalyticsUtils.executeSql(sparkSQl, manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            logger.error("Error while executing spark SQL", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing JSON", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/observation-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchObservation(@RequestBody SearchObj searchObj) {
        String data = "";
        ObservationSearchFilter observationSearchFilter = new ObservationSearchFilter();
        observationSearchFilter.setRangeSearchFieldList(searchObj.getRangeFields());
        observationSearchFilter.setSearchFields(searchObj.getFields());
        observationSearchFilter.setTable(searchObj.getTableName());
        try {
            observationSearchFilter.processFields();

            SparkQueryBuilder queryBuilder = new SparkQueryBuilder();
            String sparkSQl = queryBuilder.setTableName(observationSearchFilter.getTable()).
                    setFields(observationSearchFilter.getSearchFields())
                    .setRangeFields(observationSearchFilter.getRangeSearchFieldList()).build();

            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            List<String> views = LibrehealthAnalyticsUtils.containsViews(sparkSQl);
            LibrehealthAnalyticsUtils.loadDataByViews(views.toArray(new String[views.size()]),
                    manager.getJavaSparkContext(), manager.getSparkSession());
            data = LibrehealthAnalyticsUtils.executeSql(sparkSQl, manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            logger.error("Error while executing spark SQL", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing JSON", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/encounter-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchEncounter(@RequestBody SearchObj searchObj) {
        String data = "";
        EncounterSearchFilter encounterSearchFilter = new EncounterSearchFilter();
        encounterSearchFilter.setRangeSearchFieldList(searchObj.getRangeFields());
        encounterSearchFilter.setSearchFields(searchObj.getFields());
        encounterSearchFilter.setTable(searchObj.getTableName());
        try {
            encounterSearchFilter.processFields();

            SparkQueryBuilder queryBuilder = new SparkQueryBuilder();
            String sparkSQl = queryBuilder.setTableName(encounterSearchFilter.getTable()).
                    setFields(encounterSearchFilter.getSearchFields())
                    .setRangeFields(encounterSearchFilter.getRangeSearchFieldList()).build();

            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            List<String> views = LibrehealthAnalyticsUtils.containsViews(sparkSQl);
            LibrehealthAnalyticsUtils.loadDataByViews(views.toArray(new String[views.size()]),
                    manager.getJavaSparkContext(), manager.getSparkSession());
            data = LibrehealthAnalyticsUtils.executeSql(sparkSQl, manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            logger.error("Error while executing spark SQL", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing JSON", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/medication-request-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMedicationRequest(@RequestBody SearchObj searchObj) {
        String data = "";
        MedicationRequestSearchFilter medicationRequestSearchFilter = new MedicationRequestSearchFilter();
        medicationRequestSearchFilter.setRangeSearchFieldList(searchObj.getRangeFields());
        medicationRequestSearchFilter.setSearchFields(searchObj.getFields());
        medicationRequestSearchFilter.setTable(searchObj.getTableName());
        try {
            medicationRequestSearchFilter.processFields();

            SparkQueryBuilder queryBuilder = new SparkQueryBuilder();
            String sparkSQl = queryBuilder.setTableName(medicationRequestSearchFilter.getTable()).
                    setFields(medicationRequestSearchFilter.getSearchFields())
                    .setRangeFields(medicationRequestSearchFilter.getRangeSearchFieldList()).build();

            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            List<String> views = LibrehealthAnalyticsUtils.containsViews(sparkSQl);
            LibrehealthAnalyticsUtils.loadDataByViews(views.toArray(new String[views.size()]),
                    manager.getJavaSparkContext(), manager.getSparkSession());
            data = LibrehealthAnalyticsUtils.executeSql(sparkSQl, manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            logger.error("Error while executing spark SQL", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing JSON", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/diagnostic-report-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchDiagnosticReport(@RequestBody SearchObj searchObj) {
        String data = "";
        DiagnosticReportSearchFilter diagnosticReportSearchFilter = new DiagnosticReportSearchFilter();
        diagnosticReportSearchFilter.setRangeSearchFieldList(searchObj.getRangeFields());
        diagnosticReportSearchFilter.setSearchFields(searchObj.getFields());
        diagnosticReportSearchFilter.setTable(searchObj.getTableName());
        try {
            diagnosticReportSearchFilter.processFields();

            SparkQueryBuilder queryBuilder = new SparkQueryBuilder();
            String sparkSQl = queryBuilder.setTableName(diagnosticReportSearchFilter.getTable()).
                    setFields(diagnosticReportSearchFilter.getSearchFields())
                    .setRangeFields(diagnosticReportSearchFilter.getRangeSearchFieldList()).build();

            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            List<String> views = LibrehealthAnalyticsUtils.containsViews(sparkSQl);
            LibrehealthAnalyticsUtils.loadDataByViews(views.toArray(new String[views.size()]),
                    manager.getJavaSparkContext(), manager.getSparkSession());
            data = LibrehealthAnalyticsUtils.executeSql(sparkSQl, manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            logger.error("Error while executing spark SQL", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (JsonProcessingException e) {
            logger.error("Error while parsing JSON", e);
            return new ResponseEntity(data, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }
}
