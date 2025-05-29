FROM openjdk:17-alpine 
RUN mkdir application 
WORKDIR /application
COPY /target/Gateway_Service-0.0.1-SNAPSHOT.jar  Gateway_Service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Gateway_Service-0.0.1-SNAPSHOT.jar"]