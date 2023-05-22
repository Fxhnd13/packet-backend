FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java","-jar","/app/api-gateway.jar"]
EXPOSE 8181