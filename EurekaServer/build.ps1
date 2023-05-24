mvn clean package -P k8s
docker build -t maritort/eureka-server:0.0.1 .
docker push maritort/eureka-server:0.0.1
