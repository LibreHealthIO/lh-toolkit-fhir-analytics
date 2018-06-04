package org.librehealth.fhir.analytics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.librehealth.fhir.analytics.LibreHealthFHIRAnalyticsExecutionManager;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.model.SparkSQLQuery;
import org.librehealth.fhir.analytics.utils.LibrehealthAnalyticsUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Map;

@Controller
public class LibreHealthFHIRAnalyticController {

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("title", "Hello Home");
        model.put("date", new Date());
        return "home";
    }

    @RequestMapping(value = "/sql", method = RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> executeSQL(@RequestBody SparkSQLQuery queryDetails) {
        String data = "";
        try {
            LibreHealthFHIRAnalyticsExecutionManager manager = LibreHealthFHIRAnalyticsExecutionManager.getInstance();
            data = LibrehealthAnalyticsUtils.executeSql(queryDetails.getQuery(), manager.getSparkSession());
        } catch (LibreHealthFHIRAnalyticsException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(data, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value = "/sql", method = RequestMethod.GET)
    public ResponseEntity<?> executeSQLGet() {

        return new ResponseEntity("Successfully login", new HttpHeaders(), HttpStatus.OK);
    }
}
