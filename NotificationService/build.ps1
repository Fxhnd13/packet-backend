mvn clean package -P k8s
docker build -t maritort/notification-service:0.0.1 .
docker push maritort/notification-service:0.0.1
