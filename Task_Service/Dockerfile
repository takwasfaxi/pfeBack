FROM openjdk:17-alpine 
RUN mkdir application 
WORKDIR /application
COPY /target/Task_Service-0.0.1-SNAPSHOT.jar  Task_Service-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","Task_Service-0.0.1-SNAPSHOT.jar"]