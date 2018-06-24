**Running LibreHealth Web Analytics**

Install Apache Cassandra 2.2.12 [Download ](http://cassandra.apache.org/download/)

Install Apache Tomcat 8.5.31 [Download ](http://www-eu.apache.org/dist/tomcat/tomcat-8/v8.5.31/bin/apache-tomcat-8.5.31.tar.gz)

Pre-requisites

- Java 1.8
- Maven 3.5.x

Run Project
- Build the project by navigating to the project directory
- Start the cassandra server
- Get the war archive generated in the target directory of the project and deploy in the tomcat container
- Access the web UI from (http://localhost:8080/librehealth-fhir-analytics-1.0.0-SNAPSHOT/)
- Login with admin/admin and navigate to SQL tab
- Execute Spark SQL on sample data ex : SELECT patient.id, observation.id, observation.subject, observation.valueQuantity FROM patient inner join observation where observation.subject.reference == patient.id and observation.valueQuantity.value > 15

Configure the project with Java IDE
- Run the spring boot application class
- Login to FHIR Analytics(http://localhost:8080) application using credentials admin/admin
- Navigate to SQL table
- Enter query ex: SELECT * FROM patient inner join observation where observation.subject.reference == patient.id and gender='male'
