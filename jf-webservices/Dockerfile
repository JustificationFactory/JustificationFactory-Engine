FROM jetty
MAINTAINER Antoine Aubé (aube.antoine@protonmail.com)

WORKDIR /var/lib/jetty

COPY ./jf-bus/target/jf-bus.war ./webapps/bus.war
COPY ./jf-engine-ws/target/jf-engine-ws.war ./webapps/engine.war

EXPOSE 8080