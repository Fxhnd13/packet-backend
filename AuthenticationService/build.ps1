mvn clean package -P k8s
docker build -t maritort/authentication-service:0.0.1 .
docker push maritort/authentication-service:0.0.1
