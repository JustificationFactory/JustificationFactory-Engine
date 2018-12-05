# Justification Factory - Engine
Justification Factory engine is thye core of this framework. It modelizes and exposes features around justification semantics throught concept, operations and relationships. 
It is including :
* Justification Diagrams meta-model (jf-engine)
* Justification services (jd-ws)
  * Justification Diagram service
  * Justification Pattern service
  * Conformance service
* Justification bus (jf-bus)
* Justification database (jf-dao)

## Stakeholders:
  * Owner: Clément Duffau ([clement.duffau.02@gmail.com](clement.duffau.02@gmail.com))
  * Contributor : Antoine Aubé ([aube.antoine@protonmail.com](aube.antoine@protonmail.com))

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


