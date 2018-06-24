package org.librehealth.fhir.analytics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.librehealth.fhir.analytics.LibreHealthFHIRAnalyticsExecutionManager;
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
    public ResponseEntity<?> searchObservation(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/encounter-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchEncounter(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";

        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/medication-request-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchMedicationRequest(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";

        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/practitioner-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchPractitioner(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";

        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/diagnosticreport-search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchDiagnosticReport(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";

        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }
}
