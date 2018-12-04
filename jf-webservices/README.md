# Justification Factory :: Web Services

## Content

There is two webservices which come with Justification Factory:
* The engine webservice (jf-engine-ws) to manipulate JDs and JPDs.
* The bus (jf-bus) which automates the production of Justification Diagrams.

## Build

* Build the WARs with ```mvn clean package```.
* Build the Docker images with the script in the parent directory ```../build-docker-images.sh```.

There is a Docker image for each service (bus & engine-ws), and an image which runs both (jf-stack).

## Run

You may either use a Jetty application server or run the Docker images.
Both jf-bus and jf-engine-ws use a MongoDB database which must be specified (see README.md in both modules).

There is a docker-compose file which setup all the environment (services and database) properly.

Beware:
* If you use the Docker images, the urls are like ```[hostname:hostPort]/[bus or engine]/rest/[serviceName]/...```.
* Do not forget to setup the environment variables.

## How use it?

You can import ```docs/jf-postman.json``` which contains some requests to interact with the bus and engine-ws.
You need to setup an environment with some variables (stackHostName, stackHostPort, justificationSystemName, ...) before it is usable.