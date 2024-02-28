# Trasfer-Application

#### PREREQUISITE
Make sure you have java 17 and maven installed/configured on your system

### How to import the Project in eclipse:-
1. Right click on package explorer
2. Select Import as existing maven project
3. Right click -> Configure Build Path -> 
	Java build path -> Add library -> Add JRE 17 and remove any existing one.

### How to Execute the Project using command line:-
1. Go to Root folder /ASSIGNMENT-TRANSFER-SERVCIE of project at terminal
2. Execute command for installation of project ./mvnw clean install
3. Execute command for running the project ./mvnw spring-boot:run

### How to Execute the Project using eclipse:-
Right click on main file TransferServiceApplication.java -> Run as Java application

### How to configure a Profile for Development , Testing and Production Environment :-
1. In a text editor, Open the file at location /src/main/resources/application.properties
2. spring.profiles.active should be set to "dev" ,"test" or "prod" depending on the environment.


### API Documentation
All endpoint information can be looked up on swagger
http://localhost:8081/swagger-ui/


### App Design
The application is developed using Java 17, SpringBoot, SpringJPA with in-memory H2 database.

## Areas of Improvement
1. Add unit tests for entire code (could not do it due to time constraint)
2. API authentication using Spring Security and add roles & views
3. We can have another code base for functional tests using cucumber