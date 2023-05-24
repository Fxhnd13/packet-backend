mvn clean package -P k8s
docker build -t maritort/route-service:0.0.1 .
docker push maritort/route-service:0.0.1
