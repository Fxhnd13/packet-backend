mvn clean package -P k8s
docker build -t maritort/payment-service:0.0.1 .
docker push maritort/payment-service:0.0.1
