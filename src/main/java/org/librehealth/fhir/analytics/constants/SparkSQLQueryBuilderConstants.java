package org.librehealth.fhir.analytics.constants;

public class SparkSQLQueryBuilderConstants {
    public static String QUERY_TEMPLATE = "SELECT * FROM {0} ";
    public static String QUERY_WHERE_CLAUSE_STRING_EQ_TEMPLATE = "{0} {1} = ''{2}'' ";
    public static String QUERY_WHERE_CLAUSE_LIKE_TEMPLATE = "{0} {1} LIKE ''%{2}%'' ";
    public static String QUERY_WHERE_CLAUSE_GENERAL_TEMPLATE = "{0} {1} = {2} ";
    public static String QUERY_WHERE_CLAUSE_STARTING_STRING_EQ_TEMPLATE = "WHERE {0} = ''{1}'' ";
    public static String QUERY_WHERE_CLAUSE_STARTING_LIKE_TEMPLATE = "WHERE {0} LIKE ''%{1}%'' ";
    public static String QUERY_WHERE_CLAUSE_STARTING_GENERAL_TEMPLATE = "WHERE {0} = {1} ";
    public static String QUERY_WHERE_CLAUSE_DATE_TEMPLATE = "{0} {1} = DATE(''{2}'') ";
    public static String QUERY_WHERE_CLAUSE_STARTING_DATE_TEMPLATE = "{0} = DATE('{1}') ";

    public static String QUERY_WHERE_CLAUSE_RANGE_STARTING_GENERAL_TEMPLATE = "WHERE {0} > {1} AND {2} < {3} ";
    public static String QUERY_WHERE_CLAUSE_RANGE_GENERAL_TEMPLATE = "{0} {1} > {2} AND {3} < {4} ";

    public static String QUERY_WHERE_CLAUSE_RANGE_STARTING_DATE_RANGE_TEMPLATE = "WHERE {0} > DATE(''{1}'') AND {2} < DATE(''{3}'') ";
    public static String QUERY_WHERE_CLAUSE_RANGE_DATE_RANGE_TEMPLATE = "{0} {1} > DATE(''{2}'') AND {3} < DATE(''{4}'') ";

    public static String STRING = "string";
    public static String INTEGER = "int";
    public static String BOOLEAN = "bool";
    public static String DATE = "date";
}
