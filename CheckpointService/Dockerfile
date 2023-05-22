FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} checkpoint-service.jar
ENTRYPOINT ["java","-jar","/app/checkpoint-service.jar"]
EXPOSE 8087