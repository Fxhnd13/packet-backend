FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} user-service.jar
ENTRYPOINT ["java","-jar","/app/user-service.jar"]
EXPOSE 8081