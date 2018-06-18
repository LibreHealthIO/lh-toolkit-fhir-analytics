package org.librehealth.fhir.analytics.search;

import org.librehealth.fhir.analytics.builder.RangeSearchField;
import org.librehealth.fhir.analytics.builder.SearchField;

import java.util.List;

public class PatientSearchQuery {
    private List<SearchField> searchFields;
    private List<RangeSearchField> rangeSearchFields;
}
