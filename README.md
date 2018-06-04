**Running LibreHealth Web Analytics**

Install Apache Cassandra 2.2.12 [Download ](http://cassandra.apache.org/download/)

Pre-requisites

- Java 1.8
- Maven 3.5.x

Configure the project with Java IDE
- Run the spring boot application class
- Login to FHIR Analytics application using credentials admin/admin
- Navigate to SQL table
- Enter query ex: SELECT * FROM patient inner join observation where observation.subject.reference == patient.id and gender='male'
