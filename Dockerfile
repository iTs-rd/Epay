FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} epay.jar

ENTRYPOINT ["java", "-jar", "/epay.jar"]

EXPOSE 8080