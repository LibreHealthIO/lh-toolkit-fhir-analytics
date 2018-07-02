package org.librehealth.fhir.analytics.builder;

import org.apache.commons.lang.StringUtils;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PatientAttributeSearchFilter extends BaseSearchFilter {
    private static final Map<String, String> mappings;
    private static final Logger logger = LoggerFactory.getLogger(PatientAttributeSearchFilter.class);

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("patient-id", "patient.id");

        aMap.put("patient-identifier-id", "patient.identifier[0].id");
        aMap.put("patient-identifier-use", "patient.identifier[0].use");
        aMap.put("patient-identifier-system", "patient.identifier[0].system");
        aMap.put("patient-identifier-value", "patient.identifier[0].value");

        aMap.put("patient-name-id", "patient.name[0].id");
        aMap.put("patient-name-family", "patient.name[0].family");
        aMap.put("patient-name-given", "patient.name[0].given");

        aMap.put("patient-active", "patient.active");

        aMap.put("patient-bday", "patient.birthDate");

        aMap.put("patient-telecom-use", "patient.telecom[0].family");
        aMap.put("patient-telecom-system", "patient.telecom[0].system");
        aMap.put("patient-telecom-value", "patient.telecom[0].value");

        aMap.put("patient-gender", "patient.gender");
        aMap.put("patient-deceased", "patient.deceasedBoolean");


        aMap.put("patient-address-use", "patient.address[0].use");
        aMap.put("patient-address-line", "patient.address[0].line");
        aMap.put("patient-address-city", "patient.address[0].city");
        aMap.put("patient-address-district", "patient.address[0].district");
        aMap.put("patient-address-state", "patient.address[0].state");
        aMap.put("patient-address-postalCode", "patient.address[0].postalCode");
        aMap.put("patient-address-country", "patient.address[0].country");

        aMap.put("patient-martialstatus-code", "patient.maritalStatus.code");
        aMap.put("patient-martialstatus-system", "patient.maritalStatus.system");

        aMap.put("patient-generalpractitioner-reference", "patient.generalPractitioner[0].reference");
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