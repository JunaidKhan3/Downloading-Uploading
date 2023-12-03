#Adding jdk to docker which same as project java version
FROM openjdk:11-jdk-slim as build

EXPOSE 9001

LABEL org.opencontainers.image.authors="Junaid Khan"

COPY ultimateDwdAndUpd-0.0.1-SNAPSHOT.jar ultimateDwdAndUpd-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","ultimateDwdAndUpd-0.0.1-SNAPSHOT.jar"]