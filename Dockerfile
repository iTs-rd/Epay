FROM openjdk:17-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /home/epay.jar

ENTRYPOINT ["java", "-jar", "/home/epay.jar"]

EXPOSE 8080