# Start with the base image for java runtime (initital requirement to run the project)
FROM openjdk:17-jdk-slim

MAINTAINER kuldeepsingh

COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]