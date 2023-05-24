mvn clean package -P k8s
docker build -t maritort/checkpoint-service:0.0.1 .
docker push maritort/checkpoint-service:0.0.1
