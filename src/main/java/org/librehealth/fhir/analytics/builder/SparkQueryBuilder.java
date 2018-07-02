package org.librehealth.fhir.analytics.builder;

import org.librehealth.fhir.analytics.constants.SparkSQLQueryBuilderConstants;

import java.text.MessageFormat;
import java.util.List;

public class SparkQueryBuilder {
    private BaseSearchFilter baseSearchFilter;

    public SparkQueryBuilder() {
        baseSearchFilter = new BaseSearchFilter();
    }

    public SparkQueryBuilder setTableName(String tableName) {
        this.baseSearchFilter.setTable(tableName);
        return this;
    }

    public SparkQueryBuilder setFields(List<SearchField> fields) {
        this.baseSearchFilter.setSearchFields(fields);
        return this;
    }

    public SparkQueryBuilder setRangeFields(List<RangeSearchField> fields) {
        this.baseSearchFilter.setRangeSearchFieldList(fields);
        return this;
    }

    public String build() {
        Object[] params = new Object[]{baseSearchFilter.getTable()};
        StringBuilder sqlQuery = new StringBuilder(MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_TEMPLATE, params));
        String subQuery = "";
        List<SearchField> fields = baseSearchFilter.getSearchFields();

        boolean isFieldsPresent = false;
        boolean isFirst = true;
        for (SearchField searchField : fields) {
            if (isFirst) {
                params = new Object[]{searchField.getColumn(), searchField.getValue()};
                if (SparkSQLQueryBuilderConstants.BOOLEAN.equalsIgnoreCase(searchField.getType())) {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_STARTING_GENERAL_TEMPLATE, params);
                } else if (SparkSQLQueryBuilderConstants.INTEGER.equalsIgnoreCase(searchField.getType())) {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_STARTING_GENERAL_TEMPLATE, params);
                } else if (SparkSQLQueryBuilderConstants.DATE.equalsIgnoreCase(searchField.getType())) {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_STARTING_DATE_TEMPLATE, params);
                } else {
                    if (searchField.isContains()) {
                        subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_STARTING_LIKE_TEMPLATE, params);
                    } else {
                        subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_STARTING_STRING_EQ_TEMPLATE, params);
                    }
                }
                sqlQuery.append(subQuery);
                isFirst = false;
                isFieldsPresent = true;
            } else {
                params = new Object[]{searchField.getOperator(), searchField.getColumn(), searchField.getValue()};
                if (SparkSQLQueryBuilderConstants.BOOLEAN.equalsIgnoreCase(searchField.getType())) {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_GENERAL_TEMPLATE, params);
                } else if (SparkSQLQueryBuilderConstants.INTEGER.equalsIgnoreCase(searchField.getType())) {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_GENERAL_TEMPLATE, params);
                }  else if (SparkSQLQueryBuilderConstants.DATE.equalsIgnoreCase(searchField.getType())) {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_DATE_TEMPLATE, params);
                } else {
                    if (searchField.isContains()) {
                        subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_LIKE_TEMPLATE, params);
                    } else {
                        subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_STRING_EQ_TEMPLATE, params);
                    }
                }
                sqlQuery.append(subQuery);
            }
        }

        List<RangeSearchField> rangeSearchFields = baseSearchFilter.getRangeSearchFieldList();
        for (RangeSearchField rangeSearchField : rangeSearchFields) {
            if (!isFieldsPresent) {
                params = new Object[]{rangeSearchField.getColumn(), rangeSearchField.getValueStart(),
                        rangeSearchField.getColumn(), rangeSearchField.getValueEnd()};
                if (SparkSQLQueryBuilderConstants.DATE.equalsIgnoreCase(rangeSearchField.getType())) {
                    subQuery = MessageFormat.format(
                            SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_RANGE_STARTING_DATE_RANGE_TEMPLATE, params);
                } else if (SparkSQLQueryBuilderConstants.INTEGER.equalsIgnoreCase(rangeSearchField.getType())) {
                    subQuery = MessageFormat.format(
                            SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_RANGE_STARTING_INT_RANGE_TEMPLATE, params);
                } else {
                    subQuery = MessageFormat.format(
                            SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_RANGE_STARTING_GENERAL_TEMPLATE, params);
                }
                sqlQuery.append(subQuery);
            } else {
                params = new Object[]{rangeSearchField.getOperator(),
                        rangeSearchField.getValueStart(), rangeSearchField.getColumn(), rangeSearchField.getValueEnd()};
                if (SparkSQLQueryBuilderConstants.DATE.equalsIgnoreCase(rangeSearchField.getType())) {
                    subQuery = MessageFormat.format(
                            SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_RANGE_DATE_RANGE_TEMPLATE, params);
                } else if (SparkSQLQueryBuilderConstants.INTEGER.equalsIgnoreCase(rangeSearchField.getType())) {
                    subQuery = MessageFormat.format(
                            SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_RANGE_INT_RANGE_TEMPLATE, params);
                } else {
                    subQuery = MessageFormat.format(SparkSQLQueryBuilderConstants.QUERY_WHERE_CLAUSE_RANGE_GENERAL_TEMPLATE, params);
                }
                sqlQuery.append(subQuery);
            }
        }
        return sqlQuery.toString();
    }
}
