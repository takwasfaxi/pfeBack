FROM openjdk:17-alpine 
RUN mkdir application 
WORKDIR /application
COPY /target/Project_Service-0.0.1-SNAPSHOT.jar  Project_Service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Project_Service-0.0.1-SNAPSHOT.jar"]