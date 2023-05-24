mvn clean package -P k8s
docker build -t maritort/package-service:0.0.1 .
docker push maritort/package-service:0.0.1
