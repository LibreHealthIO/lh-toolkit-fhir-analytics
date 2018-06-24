package org.librehealth.fhir.analytics.model;

import org.librehealth.fhir.analytics.builder.RangeSearchField;
import org.librehealth.fhir.analytics.builder.SearchField;

import java.util.List;

public class SearchObj {
    private String tableName;
    private List<SearchField> fields;
    private List<RangeSearchField> rangeFields;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<SearchField> getFields() {
        return fields;
    }

    public void setFields(List<SearchField> fields) {
        this.fields = fields;
    }

    public List<RangeSearchField> getRangeFields() {
        return rangeFields;
    }

    public void setRangeFields(List<RangeSearchField> rangeFields) {
        this.rangeFields = rangeFields;
    }
}
