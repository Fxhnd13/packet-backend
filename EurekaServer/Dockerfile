FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} eureka-server.jar
ENTRYPOINT ["java","-jar","/app/eureka-server.jar"]
EXPOSE 8761