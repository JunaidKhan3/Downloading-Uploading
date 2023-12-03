#Adding jdk to docker which same as project java version
FROM openjdk:11-jdk-slim as build

EXPOSE 8080

LABEL org.opencontainers.image.authors="Junaid Khan"
ADD target/ultimateDwdAndUpd.jar ultimateDwdAndUpd.jar
#COPY ultimateDwdAndUpd-0.0.1-SNAPSHOT.jar ultimateDwdAndUpd-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","ultimateDwdAndUpd.jar"]