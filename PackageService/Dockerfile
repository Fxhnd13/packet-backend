FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} package-service.jar
ENTRYPOINT ["java","-jar","/package-service.jar"]
EXPOSE 8083