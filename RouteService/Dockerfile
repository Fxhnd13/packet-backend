FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} route-service.jar
ENTRYPOINT ["java","-jar","/route-service.jar"]
EXPOSE 8084