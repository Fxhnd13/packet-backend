# Información general
Proyecto con distintos microservicios para la gestión y control de paquetes distribuidos internacionalmente.

# Configuración
## Contenedores
### Kafka y zookeeper
`docker pull wurstmeister/kafka`
`docker network create kafka-net`
`docker run -d --name zookeeper --network kafka-net zookeeper`
`docker run -d --name kafka --network kafka-net -p 9092:9092 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 wurstmeister/kafka`

**Nota relevante: Si por algún motivo no funciona correctamente con los comandos anteriores eliminar los contenedores y ejecutar nuevamente los comandos, reemplazando el último por el siguiente:**
`docker run -d --name kafka --network kafka-net -p 9092:9092 -e KAFKA_ADVERTISED_HOST_NAME=<ip_maquina> -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 wurstmeister/kafka`

### Postrgres
`docker pull postgres`
`docker run --name database -e POSTGRES_PASSWORD=**password** -p 5432:5432 -d postgres`

Para ingresar a la instancia de postgres desde la línea de comandos ejecutar:
`docker exec -it database psql -U postgres`

### Redis
`docker run --name redis -p 6379:6379 -d redis`
## Documentación de microservicios y datos importantes
### src/main/java/resources/application.properties
En este archivo de configuraciones únicamente deberán ser cambiadas las credenciales para la conexión con postgres y para la conexión con Redis, por lo que el resto deberán mantenerse
### src/main/java/resources/application.yml
En este archivo no hace falta realizar modificaciones para ninguno de los microservicios, pero también contiene propiedades referentes a eureka y a la aplicación per se.
## Interacción Gateway y Autenticación
1. Gateway
    - Aquí se encuentra una clase denominada 'DefaultFilter' en la que se encuentran abiertas aquellas rutas que pueden ser accesibles por cualquier usuario
        - Registrar un usuario /authentication-service/v1/auth/register
        - Login /authentication-service/v1/auth/authenticate
2. Default filter
    - Verifica que el recurso solicitado no sea uno de los dos abiertos generalmente
    - Verifica si la petición tiene el encabezado 'Authorization', caso contrario retorna error 401 - Unathorized
    - De tener el token, entonces verifica la validez del token accediendo al método getUserRole, que nos retorna los roles de un token en caso de ser válido.
        - El token debe estar firmado por la llave que se encuentra en el servicio de autenticación
        - El token debe tener un tiempo desde el último uso menor a 20 minutos
    - En caso de tener el token válido redirecciona al microservicio deseado.
3. PreHandler + Anotación 'RoleValidator'
    - Se obtienen los roles del usuario utilizando la ruta /authentication-service/v1/auth/roles