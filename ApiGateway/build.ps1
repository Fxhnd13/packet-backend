mvn clean package -P k8s
docker build -t maritort/api-gateway:0.0.1 .
docker push maritort/api-gateway:0.0.1
