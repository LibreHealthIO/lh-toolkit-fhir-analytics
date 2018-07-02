package org.librehealth.fhir.analytics.builder;

import org.apache.commons.lang.StringUtils;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EncounterSearchFilter extends BaseSearchFilter {
    private static final Map<String, String> mappings;
    private static final Logger logger = LoggerFactory.getLogger(EncounterSearchFilter.class);

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("encounter-id", "encounter.id");

        aMap.put("encounter-identifier-id", "encounter.identifier[0].id");
        aMap.put("encounter-identifier-use", "encounter.identifier[0].use");
        aMap.put("encounter-identifier-system", "encounter.identifier[0].system");
        aMap.put("encounter-identifier-value", "encounter.identifier[0].value");

        aMap.put("encounter-status", "encounter.status");

        aMap.put("encounter-class-code", "encounter.code.code");
        aMap.put("encounter-class-system", "encounter.code.system");

        aMap.put("encounter-type-code", "encounter.type.coding[0].code");
        aMap.put("encounter-type-system", "encounter.type.coding[0].system");

        aMap.put("encounter-priority-code", "encounter.priority.coding[0].code");
        aMap.put("encounter-priority-system", "encounter.priority.coding[0].system");

        aMap.put("encounter-subject-reference", "encounter.subject.reference");
        aMap.put("encounter-appointment-reference", "encounter.appointment.reference");

        aMap.put("encounter-length", "encounter.length.value");

        aMap.put("encounter-reason-code", "encounter.reason[0].element.coding[0].code");
        aMap.put("encounter-reason-system", "encounter.reason[0].element.coding[0].system");

        aMap.put("encounter-diagnosis-condition-reference", "encounter.diagnosis[0].element.condition.reference");
        aMap.put("encounter-diagnosis-condition-role-code", "encounter.diagnosis[0].element.role.coding[0].code");
        aMap.put("encounter-diagnosis-condition-role-system", "encounter.diagnosis[0].element.role.coding[0].system");

        aMap.put("encounter-location-reference", "encounter.location[0].element.location.reference");
        aMap.put("encounter-serviceprovider-reference", "encounter.serviceProvider.reference");


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