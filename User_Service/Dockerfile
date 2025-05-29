#l'image de base : openjdk
FROM openjdk:17-alpine 
#créer un dossier dans l'image docker qui va etre mon workspace
RUN mkdir application 
#Toutes les commandes suivantes (COPY, CMD, etc.) seront exécutées à partir de ce dossier /application.
WORKDIR /application
COPY /target/User_Service-0.0.1-SNAPSHOT.jar  User_Service-0.0.1-SNAPSHOT.jar 
CMD ["java","-jar","User_Service-0.0.1-SNAPSHOT.jar"]