package org.librehealth.fhir.analytics.model;

import java.util.List;

public class SparkSQLOutput {

    String[] columns;
    List<String[]> data;

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
}
