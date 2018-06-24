package org.librehealth.fhir.analytics.builder;

import java.util.List;

public class BaseSearchFilter {
    private List<RangeSearchField> rangeSearchFieldList;
    private List<SearchField> searchFields;
    private String table;

    public List<RangeSearchField> getRangeSearchFieldList() {
        return rangeSearchFieldList;
    }

    public void setRangeSearchFieldList(List<RangeSearchField> rangeSearchFieldList) {
        this.rangeSearchFieldList = rangeSearchFieldList;
    }

    public List<SearchField> getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(List<SearchField> searchFields) {
        this.searchFields = searchFields;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
