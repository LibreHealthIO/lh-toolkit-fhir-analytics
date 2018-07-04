package org.librehealth.fhir.analytics.builder;

import org.apache.commons.lang.StringUtils;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DiagnosticReportSearchFilter extends BaseSearchFilter {
    private static final Map<String, String> mappings;
    private static final Logger logger = LoggerFactory.getLogger(DiagnosticReportSearchFilter.class);

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("diagnostic-report-id", "diagnosticreport.id");

        aMap.put("diagnostic-report-identifier-id", "diagnosticreport.identifier[0].id");
        aMap.put("diagnostic-report-identifier-use", "diagnosticreport.identifier[0].use");
        aMap.put("diagnostic-report-identifier-system", "diagnosticreport.identifier[0].system");
        aMap.put("diagnostic-report-identifier-value", "diagnosticreport.identifier[0].value");

        aMap.put("diagnostic-report-basedon-reference", "diagnosticreport.basedOn.reference");
        aMap.put("diagnostic-report-status", "diagnosticreport.status");

        aMap.put("diagnostic-report-category-code", "diagnosticreport.category.coding[0].code");
        aMap.put("diagnostic-report-category-system", "diagnosticreport.category.coding[0].system");

        aMap.put("diagnostic-report-code-code", "diagnosticreport.code.coding[0].code");
        aMap.put("diagnostic-report-code-system", "diagnosticreport.code.coding[0].system");

        aMap.put("diagnostic-report-subject-reference", "diagnosticreport.subject.reference");
        aMap.put("diagnostic-report-context-reference", "diagnosticreport.context.reference");

        aMap.put("diagnostic-report-performer-role-code", "diagnosticreport.performer[0].role.coding[0].code");
        aMap.put("diagnostic-report-performer-role-system", "diagnosticreport.performer[0].role.coding[0].system");
        aMap.put("diagnostic-report-performer-actor-reference", "diagnosticreport.performer[0].actor.reference");

        aMap.put("diagnostic-report-specimen-reference", "diagnosticreport.specimen[0].reference");
        aMap.put("diagnostic-report-result-reference", "diagnosticreport.result[0].reference");
        aMap.put("diagnostic-report-image-study-reference", "diagnosticreport.imagingStudy[0].reference");

        aMap.put("diagnostic-report-conclusion", "diagnosticreport.conclusion");

        aMap.put("diagnostic-report-coded-diagnosis-system", "diagnosticreport.codedDiagnosis[0].coding[0].system");
        aMap.put("diagnostic-report-coded-diagnosis-code", "diagnosticreport.codedDiagnosis[0].coding[0].code");


        mappings = Collections.unmodifiableMap(aMap);
    }

    public void processFields() throws LibreHealthFHIRAnalyticsException {
        for (RangeSearchField rangeSearchField : getRangeSearchFieldList()) {
            String mappingColumn = mappings.get(rangeSearchField.getColumn());
            if (!StringUtils.isEmpty(mappingColumn)) {
                rangeSearchField.setColumn(mappingColumn);
            } else {
                String msg = "Can't find mapping column for " + rangeSearchField.getColumn();
                logger.info(msg);
                throw new LibreHealthFHIRAnalyticsException(msg);
            }
        }

        for (SearchField searchField : getSearchFields()) {
            String mappingColumn = mappings.get(searchField.getColumn());
            if (!StringUtils.isEmpty(mappingColumn)) {
                searchField.setColumn(mappingColumn);
            } else {
                String msg = "Can't find mapping column for " + searchField.getColumn();
                logger.info(msg);
                throw new LibreHealthFHIRAnalyticsException(msg);
            }
        }
    }

}