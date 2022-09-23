# Banking-Account-Application

This is a standalone java application which allows to transfer and withdraw money from an account. It is possible to pay
with either debit card or credit card. If a transfer/withdraw is done with a credit card, 1% of the amount is charged extra. I covers below criterias

A negative balance is not possible
Account should contain at least some user details, card details and current balance
One rest endpoint to see current available balance in all accounts
One rest endpoint to withdraw money
One rest endpoint to transfer money
One credit card or debit card is linked with one account
It should be able to audit transfers or withdrawals 

#### PREREQUISITE
Make sure you have java 11 and maven installed/configured on your system
* [Java installation guide](https://www.codejava.net/java-se/download-and-install-java-11-openjdk-and-oracle-jdk)
* [Maven installation guide](https://maven.apache.org/install.html)

### How to import the Project in eclipse:-
1. Right click on package explorer
2. Select Import as existing maven project
3. Right click -> Configure Build Path -> 
	a) Java build path -> Add library -> Add JRE 11 and remove any existing one.
	b) Java Compiler -> Select JDK 1.8 or above.

### How to Execute the Project using command line:-
1. Go to Root folder /ASSIGNMENT-BANKING-ACCOUNT of project at terminal
2. Execute command for installation of project ./mvnw clean install
3. Execute command for running the project ./mvnw spring-boot:run

### How to Execute the Project using eclipse:-
Right click on main file BankAccountManagerApplication.java -> Run as Java application

### How to configure a Profile for Development , Testing and Production Environment :-
1. In a text editor, Open the file at location /src/main/resources/application.properties
2. spring.profiles.active should be set to "dev" ,"test" or "prod" depending on the environment.


### API Documentation
All endpoint information can be looked up on swagger
http://localhost:8080/swagger-ui/


## Dev Testing
A manual testing word document is included on some dev test case snapshots.

### Recipe App Design
The application is developed using Java 11, SpringBoot, SpringJPA with in-memory H2 database.

## Areas of Improvement
1. API authentication using Spring Security and add roles & views
2. We can have another code base for functional tests using cucumber