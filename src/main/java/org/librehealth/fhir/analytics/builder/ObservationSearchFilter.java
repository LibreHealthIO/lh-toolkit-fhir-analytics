package org.librehealth.fhir.analytics.builder;

import org.apache.commons.lang.StringUtils;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ObservationSearchFilter extends BaseSearchFilter {
    private static final Map<String, String> mappings;
    private static final Logger logger = LoggerFactory.getLogger(ObservationSearchFilter.class);

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("observation-id", "observation.id");

        aMap.put("observation-identifier-id", "observation.identifier[0].id");
        aMap.put("observation-identifier-use", "observation.identifier[0].use");
        aMap.put("observation-identifier-system", "observation.identifier[0].system");
        aMap.put("observation-identifier-value", "observation.identifier[0].value");
        aMap.put("observation-basedon-reference", "observation.basedOn.reference");

        aMap.put("observation-status", "observation.status");

        aMap.put("observation-category-code", "observation.category.coding[0].code");
        aMap.put("observation-category-system", "observation.category.coding[0].system");

        aMap.put("observation-type-code", "observation.code.coding[0].code");
        aMap.put("observation-type-system", "observation.code.coding[0].system");

        aMap.put("observation-context-reference", "observation.context.reference");

        aMap.put("observation-performer-reference", "observation.performer[0].reference");

        aMap.put("observation-value-codeable-code", "observation.valueCodeableConcept.coding[0].code");
        aMap.put("observation-value-codeable-system", "observation.valueCodeableConcept.coding[0].system");
        aMap.put("observation-value-boolean", "observation.valueBoolean");

        aMap.put("observation-value-valueQuantity", "observation.valueQuantity.value");

        aMap.put("observation-value-quantity-range-low", "observation.valueRange.low.value");
        aMap.put("observation-value-quantity-range-high", "observation.valueRange.high.value");


        aMap.put("observation-bodysite-code", "observation.bodySite.coding[0].code");
        aMap.put("observation-bodysite-system", "observation.bodySite.coding[0].system");

        aMap.put("observation-method-code", "observation.method.coding[0].code");
        aMap.put("observation-method-system", "observation.method.coding[0].system");

        aMap.put("observation-specimen-reference", "observation.specimen.reference");
        aMap.put("observation-device-reference", "observation.device.reference");

        aMap.put("observation-specimen-reference", "observation.specimen.reference");
        aMap.put("observation-device-reference", "observation.device.reference");

        aMap.put("observation-reference-range-low", "observation.referenceRange.low.value");
        aMap.put("observation-reference-range-high", "observation.referenceRange.high.value");

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