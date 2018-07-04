package org.librehealth.fhir.analytics.builder;

import org.apache.commons.lang.StringUtils;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MedicationRequestSearchFilter extends BaseSearchFilter {
    private static final Map<String, String> mappings;
    private static final Logger logger = LoggerFactory.getLogger(MedicationRequestSearchFilter.class);

    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("medication-request-id", "medicationrequest.id");

        aMap.put("medication-request-identifier-id", "medicationrequest.identifier[0].id");
        aMap.put("medication-request-identifier-use", "medicationrequest.identifier[0].use");
        aMap.put("medication-request-identifier-system", "medicationrequest.identifier[0].system");
        aMap.put("medication-request-identifier-value", "medicationrequest.identifier[0].value");

        aMap.put("medication-request-definition-reference", "medicationrequest.definition.reference");
        aMap.put("medication-request-basedon-reference", "medicationrequest.basedOn.reference");
        aMap.put("medication-request-status", "medicationrequest.status");

        aMap.put("medication-request-category-code", "medicationrequest.category.coding[0].code");
        aMap.put("medication-request-category-system", "medicationrequest.category.coding[0].system");

        aMap.put("medication-request-priority", "medicationrequest.priority");

        aMap.put("medication-request-value-codeable-code", "medicationrequest.medicationCodeableConcept.coding[0].code");
        aMap.put("medication-request-value-codeable-system", "medicationrequest.medicationCodeableConcept.coding[0].system");

        aMap.put("medication-request-medication-reference", "medicationrequest.medicationReference.reference");
        aMap.put("medication-request-subject-reference", "medicationrequest.subject.reference");
        aMap.put("medication-request-context-reference", "medicationrequest.context.reference");
        aMap.put("medication-request-support-information-reference", "medicationrequest.supportingInformation[0].reference");
        aMap.put("medication-request-requester-agent-reference", "medicationrequest.requester.agent.reference");
        aMap.put("medication-request-requester-onbehalfof-reference", "medicationrequest.requester.onBehalfOf.reference");

        aMap.put("medication-request-requester-reason-code-system", "medicationrequest.reasonCode.coding[0].system");
        aMap.put("medication-request-requester-reason-code-code", "medicationrequest.reasonCode.coding[0].code");
        aMap.put("medication-request-reason-reference", "medicationrequest.dosageInstruction.reference");

        aMap.put("medication-request-dosing-instructions-site-system", "medicationrequest.dosageInstruction[0].site.coding[0].system");
        aMap.put("medication-request-dosing-instructions-site-code", "medicationrequest.dosageInstruction[0].site.coding[0].code");

        aMap.put("medication-request-dosing-instructions-route-system", "medicationrequest.dosageInstruction[0].route.coding[0].system");
        aMap.put("medication-request-dosing-instructions-route-code", "medicationrequest.dosageInstruction[0].route.coding[0].code");

        aMap.put("medication-request-dosing-instructions-method-system", "medicationrequest.dosageInstruction[0].method.coding[0].system");
        aMap.put("medication-request-dosing-instructions-method-code", "medicationrequest.dosageInstruction[0].method.coding[0].code");

        aMap.put("medication-request-substitution-system", "medicationrequest.substitution.reason.coding[0].system");
        aMap.put("medication-request-substitution-code", "medicationrequest.substitution.reason.coding[0].code");

        aMap.put("medication-request-prior-prescription-reference", "medicationrequest.priorPrescription.reference");

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