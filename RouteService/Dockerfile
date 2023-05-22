FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} route-service.jar
ENTRYPOINT ["java","-jar","/app/route-service.jar"]
EXPOSE 8084