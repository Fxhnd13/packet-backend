FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} notification-service.jar
ENTRYPOINT ["java","-jar","/app/notification-service.jar"]
EXPOSE 8082