## Demo
This project is a POC for microservices which has different flavours of bckend written in **REST**, **Reactive** and **Websockets**. The frotnend is written in **Angular 8**.

The project can be run using docker-compose or deployed in Kubernetes.

### Build Backend
To build docker images for backend run following command:
```sh
mvn clean install -Ddocker
```
### Build Frontend

To build docker image for frontend, run the following command inside `frontend` folder.
```sh
docker build -t poc-frontend .
```
Note- Whatever image names you give, change in the `k8s` nd `docker-compose.yml` accordingly.

### Endpoints

#### User Service (REST)

**Endpoint** - `/users/getPublicMailingAddress` 

If calling via gateway then - `/user-service/users/getPublicMailingAddress`

#### Contact Us Service (REST)

**Endpoint** - `/contactUs/address` 

If calling via gateway then - `/contact-us-service/contactUs/address`
This internally calls `user-service` and produce appended result.

#### Reactive Web Producer (Reactive)

**Endpoint**  - `/greetings/sse`  and `/greetings`

If calling via gateway then - `/reactive-web-producer/greetings/sse`

#### WebSocket Service  (Websocket)

**Websocker Connect Endpoint** - `/gs-guide-websocket`  
**Topic** - `/topic/greetings`
**Message Mapping** - `/hello`

If calling via gateway then - `/websocketservice/gs-guide-websocket`

### Profiles
Each backend project has both `eureka-client` and `spring-cloud-kubernetes` jars. 

If run by `classical` profile, it expects Eureka server to be present.

When running on Kubernetes, each service is inside a "Deployment" and exposed via "Service". Therefore, we don't need Eureka as a service discovery mechanism and we can use the services list inside K8 itself. So run with `k8` profile.
