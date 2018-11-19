# Justification Factory - Engine
Justification Factory engine is thye core of this framework. It modelize and expose features around justification semantics throught concept, operations and relationships. 
It is including :
* Justification Diagrams meta-model 
* Justification services
  * Justification Diagram service
  * Justification Pattern service
  * Conformance service
* Justification bus
* Justification database

## Key concepts in meta-model

## Services documentation

## Dependencies needed

You need this set of tools :
* Java 8
* Maven
* Docker
* MongoDB server

## To run the platform

* Compile and run tests ``` mvn clean install```
* Run services for development (embedded database) ```cd jf-webservices/jf-engine-ws && mvn jetty:run``` and ```cd jf-webservices/jf-bus && mvn jetty:run```
* Run services for deployment : ```cd jf-webservices && docker compose```


