FROM openjdk:17
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java","-jar","/app/payment-service.jar"]
EXPOSE 8088