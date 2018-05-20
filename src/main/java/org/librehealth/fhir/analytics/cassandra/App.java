package org.librehealth.fhir.analytics.cassandra;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import com.cerner.bunsen.FhirEncoders;
import com.datastax.driver.core.Session;
import com.datastax.spark.connector.cql.CassandraConnector;
import com.datastax.spark.connector.japi.CassandraRow;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.hl7.fhir.dstu3.model.Patient;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

public class App implements Serializable {

	private static String patient = "{\n" +
			"  \"resourceType\": \"Patient\",\n" +
			"  \"id\": \"example\",\n" +
			"  \"text\": {\n" +
			"    \"status\": \"generated\",\n" +
			"    \"div\": \"<div xmlns=\\\"http://www.w3.org/1999/xhtml\\\">\\n\\t\\t\\t<table>\\n\\t\\t\\t\\t<tbody>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Name</td>\\n\\t\\t\\t\\t\\t\\t<td>Peter James \\n              <b>Chalmers</b> (&quot;Jim&quot;)\\n            </td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Address</td>\\n\\t\\t\\t\\t\\t\\t<td>534 Erewhon, Pleasantville, Vic, 3999</td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Contacts</td>\\n\\t\\t\\t\\t\\t\\t<td>Home: unknown. Work: (03) 5555 6473</td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t\\t<tr>\\n\\t\\t\\t\\t\\t\\t<td>Id</td>\\n\\t\\t\\t\\t\\t\\t<td>MRN: 12345 (Acme Healthcare)</td>\\n\\t\\t\\t\\t\\t</tr>\\n\\t\\t\\t\\t</tbody>\\n\\t\\t\\t</table>\\n\\t\\t</div>\"\n" +
			"  },\n" +
			"  \"identifier\": [\n" +
			"    {\n" +
			"      \"use\": \"usual\",\n" +
			"      \"type\": {\n" +
			"        \"coding\": [\n" +
			"          {\n" +
			"            \"system\": \"http://hl7.org/fhir/v2/0203\",\n" +
			"            \"code\": \"MR\"\n" +
			"          }\n" +
			"        ]\n" +
			"      },\n" +
			"      \"system\": \"urn:oid:1.2.36.146.595.217.0.1\",\n" +
			"      \"value\": \"12345\",\n" +
			"      \"period\": {\n" +
			"        \"start\": \"2001-05-06\"\n" +
			"      },\n" +
			"      \"assigner\": {\n" +
			"        \"display\": \"Acme Healthcare\"\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"active\": true,\n" +
			"  \"name\": [\n" +
			"    {\n" +
			"      \"use\": \"official\",\n" +
			"      \"family\": \"Chalmers\",\n" +
			"      \"given\": [\n" +
			"        \"Peter\",\n" +
			"        \"James\"\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"use\": \"usual\",\n" +
			"      \"given\": [\n" +
			"        \"Jim\"\n" +
			"      ]\n" +
			"    },\n" +
			"    {\n" +
			"      \"use\": \"maiden\",\n" +
			"      \"family\": \"Windsor\",\n" +
			"      \"given\": [\n" +
			"        \"Peter\",\n" +
			"        \"James\"\n" +
			"      ],\n" +
			"      \"period\": {\n" +
			"        \"end\": \"2002\"\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"telecom\": [\n" +
			"    {\n" +
			"      \"use\": \"home\"\n" +
			"    },\n" +
			"    {\n" +
			"      \"system\": \"phone\",\n" +
			"      \"value\": \"(03) 5555 6473\",\n" +
			"      \"use\": \"work\",\n" +
			"      \"rank\": 1\n" +
			"    },\n" +
			"    {\n" +
			"      \"system\": \"phone\",\n" +
			"      \"value\": \"(03) 3410 5613\",\n" +
			"      \"use\": \"mobile\",\n" +
			"      \"rank\": 2\n" +
			"    },\n" +
			"    {\n" +
			"      \"system\": \"phone\",\n" +
			"      \"value\": \"(03) 5555 8834\",\n" +
			"      \"use\": \"old\",\n" +
			"      \"period\": {\n" +
			"        \"end\": \"2014\"\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"gender\": \"male\",\n" +
			"  \"birthDate\": \"1974-12-25\",\n" +
			"  \"_birthDate\": {\n" +
			"    \"extension\": [\n" +
			"      {\n" +
			"        \"url\": \"http://hl7.org/fhir/StructureDefinition/patient-birthTime\",\n" +
			"        \"valueDateTime\": \"1974-12-25T14:35:45-05:00\"\n" +
			"      }\n" +
			"    ]\n" +
			"  },\n" +
			"  \"deceasedBoolean\": false,\n" +
			"  \"address\": [\n" +
			"    {\n" +
			"      \"use\": \"home\",\n" +
			"      \"type\": \"both\",\n" +
			"      \"text\": \"534 Erewhon St PeasantVille, Rainbow, Vic  3999\",\n" +
			"      \"line\": [\n" +
			"        \"534 Erewhon St\"\n" +
			"      ],\n" +
			"      \"city\": \"PleasantVille\",\n" +
			"      \"district\": \"Rainbow\",\n" +
			"      \"state\": \"Vic\",\n" +
			"      \"postalCode\": \"3999\",\n" +
			"      \"period\": {\n" +
			"        \"start\": \"1974-12-25\"\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"contact\": [\n" +
			"    {\n" +
			"      \"relationship\": [\n" +
			"        {\n" +
			"          \"coding\": [\n" +
			"            {\n" +
			"              \"system\": \"http://hl7.org/fhir/v2/0131\",\n" +
			"              \"code\": \"N\"\n" +
			"            }\n" +
			"          ]\n" +
			"        }\n" +
			"      ],\n" +
			"      \"name\": {\n" +
			"        \"family\": \"du Marché\",\n" +
			"        \"_family\": {\n" +
			"          \"extension\": [\n" +
			"            {\n" +
			"              \"url\": \"http://hl7.org/fhir/StructureDefinition/humanname-own-prefix\",\n" +
			"              \"valueString\": \"VV\"\n" +
			"            }\n" +
			"          ]\n" +
			"        },\n" +
			"        \"given\": [\n" +
			"          \"Bénédicte\"\n" +
			"        ]\n" +
			"      },\n" +
			"      \"telecom\": [\n" +
			"        {\n" +
			"          \"system\": \"phone\",\n" +
			"          \"value\": \"+33 (237) 998327\"\n" +
			"        }\n" +
			"      ],\n" +
			"      \"address\": {\n" +
			"        \"use\": \"home\",\n" +
			"        \"type\": \"both\",\n" +
			"        \"line\": [\n" +
			"          \"534 Erewhon St\"\n" +
			"        ],\n" +
			"        \"city\": \"PleasantVille\",\n" +
			"        \"district\": \"Rainbow\",\n" +
			"        \"state\": \"Vic\",\n" +
			"        \"postalCode\": \"3999\",\n" +
			"        \"period\": {\n" +
			"          \"start\": \"1974-12-25\"\n" +
			"        }\n" +
			"      },\n" +
			"      \"gender\": \"female\",\n" +
			"      \"period\": {\n" +
			"        \"start\": \"2012\"\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"managingOrganization\": {\n" +
			"    \"reference\": \"Organization/1\"\n" +
			"  }\n" +
			"}";
	public static void main(String[] args) {
		SparkConf conf = new SparkConf()
				.setAppName("Spark Canary (CC) - Test")
				.setMaster("local[*]")
				.set("spark.cassandra.connection.host", "127.0.0.1");
		JavaSparkContext sc = new JavaSparkContext(conf);
		CassandraConnector connector = CassandraConnector.apply(conf);

		SparkSession spark = SparkSession
				.builder()
				.appName("Java Spark SQL basic example")
				.config("spark.some.config.option", "some-value")
				.getOrCreate();

		try (Session session = connector.openSession()) {
			session.execute("DROP KEYSPACE IF EXISTS simple_canary_cc");
			session.execute("CREATE KEYSPACE simple_canary_cc WITH " +
					"REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1}");
			session.execute("CREATE TABLE simple_canary_cc.simple_pair " +
					"(key int PRIMARY KEY, value text)");
		}

		List<SimplePair> pairs = Arrays.asList(
				new SimplePair(1, patient)
		);


		JavaRDD<SimplePair> simplekvRDD = sc.parallelize(pairs);
		javaFunctions(simplekvRDD)
				.writerBuilder("simple_canary_cc", "simple_pair",
						mapToRow(SimplePair.class))
				.saveToCassandra();

		//JavaRDD<Patient> cassandraRdd = CassandraJavaUtil.javaFunctions(sc)
		//		.cassandraTable("simple_canary_cc", "simple_pair", map(String.class)).select("value");

		JavaRDD<Patient> cassandraRDD = javaFunctions(sc).cassandraTable("simple_canary_cc", "simple_pair")
				.map(new Function<CassandraRow, Patient>() {
					@Override
					public Patient call(CassandraRow cassandraRow) throws Exception {
						FhirContext ourFhirCtx = FhirContext.forDstu3();
						IParser parser=ourFhirCtx.newJsonParser().setPrettyPrint(true);
						String patientSrt = cassandraRow.getString("value");
						Patient patientOb=parser.parseResource(Patient.class, patientSrt);
						return patientOb;
					}
				});

		FhirEncoders encoders =
				FhirEncoders.forStu3().getOrCreate();
		Dataset<Patient> peopleDFq = spark.createDataset(cassandraRDD.rdd(), encoders.of(Patient.class));

	/*	FhirContext ourFhirCtx = FhirContext.forDstu3();
		IParser parser=ourFhirCtx.newJsonParser().setPrettyPrint(true);
		Patient patientOb=parser.parseResource(Patient.class, patient);

		//Encoder<Patient> personEncoder = Encoders.bean(Patient.class);
		Dataset<Patient> javaBeanDS = spark.createDataset(
				Collections.singletonList(patientOb),
				encoders.of(Patient.class)
		);
		peopleDFq.createOrReplaceTempView("patient");

		Dataset<Row> sqlDF = spark.sql("SELECT * FROM patient where gender='male'");
		sqlDF.show();

		/*JavaRDD<SimplePair> peopleRDD = spark.read()
				.textFile("examples/src/main/resources/people.txt")
				.javaRDD()
				.map(line -> {
					String[] parts = line.split(",");
					SimplePair person = new SimplePair();rim()));
					return person;
				});

// Apply a schema to an RDD of JavaBeans to get a DataFrame
		Dataset<Row> peopleDF = spark.createDataFrame(peopleRDD, SimplePair.class);

		Dataset<Row> peopleDFq = spark.createDataFrame(cassandraRdd, Patient.class);*/
		sc.stop();

	}

	public static class SimplePair implements Serializable {
		private Integer key;
		private String value;

		public SimplePair() { /* Java Bean ctor */ }

		public SimplePair(Integer key, String value) {
			this.key = key;
			this.value = value;
		}

		public Integer getKey() { return key; }
		public void setKey(Integer key) { this.key = key; }

		public String getValue() { return value; }
		public void setValue(String value) { this.value = value; }

		@Override
		public String toString() {
			return MessageFormat.format("(key: {0}, val: {1})", key, value);
		}
	}
}
