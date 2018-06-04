package org.librehealth.fhir.analytics.utils;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cerner.bunsen.FhirEncoders;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraRow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Patient;
import org.librehealth.fhir.analytics.cassandra.CassandraDataStoreService;
import org.librehealth.fhir.analytics.cassandra.CassandraDataStoreServiceImpl;
import org.librehealth.fhir.analytics.exception.LibreHealthFHIRAnalyticsException;
import org.librehealth.fhir.analytics.model.SparkSQLOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

public class LibrehealthAnalyticsUtils {

    public static String readFileAsString(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        InputStreamReader inputStreamREader = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStreamREader = new InputStreamReader(is, StandardCharsets.UTF_8);
            br = new BufferedReader(inputStreamREader);
            String content = br.readLine();
            if (content == null) {
                return sb.toString();
            }

            sb.append(content);

            while ((content = br.readLine()) != null) {
                sb.append('\n').append(content);
            }
        } finally {
            if (inputStreamREader != null) {
                try {
                    inputStreamREader.close();
                } catch (IOException ignore) {
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignore) {
                }
            }
        }
        return sb.toString();
    }


    public static String buildResponseJsonForSparkSQL(List<Row> rows, String[] columns) throws JsonProcessingException {
        SparkSQLOutput output = new SparkSQLOutput();
        output.setColumns(columns);
        int columnLength = columns.length;
        String[] data;
        Object columnContent;
        List<String[]> sparkSQLRows = new ArrayList<>();
        int j = 0;
        for (Row row : rows) {
            data = new String[columnLength];
            for (int i = 0; i < columnLength; i++) {
                columnContent = row.get(i);
                if (columnContent != null) {
                    data[i] = columnContent.toString();
                } else {
                    data[i] = Strings.EMPTY;
                }
            }
            sparkSQLRows.add(data);
        }
        output.setData(sparkSQLRows);
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(output);
        return jsonInString;
    }

    public static String executeSql(String query, SparkSession sparkSession)
                                                        throws JsonProcessingException, LibreHealthFHIRAnalyticsException {
        Dataset<Row> obsDF = sparkSession.sql(query);
        String out = LibrehealthAnalyticsUtils.buildResponseJsonForSparkSQL(obsDF.collectAsList(), obsDF.columns());
        return out;
    }
}
