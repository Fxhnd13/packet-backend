FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} package-service.jar
ENTRYPOINT ["java","-jar","/app/package-service.jar"]
EXPOSE 8083