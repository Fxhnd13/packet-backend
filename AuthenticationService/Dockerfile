FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} authentication-service.jar
ENTRYPOINT ["java","-jar","/app/authentication-service.jar"]
EXPOSE 8086