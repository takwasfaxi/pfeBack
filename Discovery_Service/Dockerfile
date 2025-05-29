FROM openjdk:17-alpine 
RUN mkdir application 
WORKDIR /application
COPY /target/Discovery_Service-0.0.1-SNAPSHOT.jar  Discovery_Service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Discovery_Service-0.0.1-SNAPSHOT.jar"]