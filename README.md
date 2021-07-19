# microservices demo:
Currency-conversion service -> Currency-Exchange service -> Database

Below are the REST used in this demo:
====================================
http://localhost:8000/currency-exchange/from/USD/to/INR

http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/5

http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

http://localhost:8761/ - Eureka

http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/EUR/to/INR  - API gateway


To Run multiple instance of the same service with different port number:
========================================================================

Eclipse -> Run configurations -> Spring Boot App -> Create new configuration -> select the project
Arguements -> VM arguements: -> -Dserver.port=8001 

concepts covered: 
================
- Spring Cloud config server
- JPA
- Load balancing:
	Rest Template,Feign - To Call another service's REST
	netflix-eureka-server - Naming server
- Spring cloud API Gateway (Spring cloud routing, Global Filter)
