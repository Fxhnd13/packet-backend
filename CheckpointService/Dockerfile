FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} checkpoint-service.jar
ENTRYPOINT ["java","-jar","/checkpoint-service.jar"]
EXPOSE 8087