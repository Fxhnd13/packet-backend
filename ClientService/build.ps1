mvn clean package -P k8s
docker build -t maritort/client-service:0.0.1 .
docker push maritort/client-service:0.0.1
