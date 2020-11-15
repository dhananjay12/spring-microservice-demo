# Demo
This project is a POC for microservices which has different flavours of backend written in **REST**, **Reactive** and **Websockets**. The frotnend is written in **Angular 8**.

The project can be run using docker-compose or deployed in Kubernetes.

## How To Run Locally

### Build and Run Backend
To build docker images for backend run following command:
```sh
mvn -pl eureka-server,service-one,service-two,reactive-service,websocket-service,gateway  -Dmaven.test.skip=true package jib:dockerBuild
```

`dockerBuild` requires your system to have the docker demon and will build images locally. `build` is more efficient but wont build images locally.
 You might need to change the `docker.image.url` in pom.xml if you are using `build`.

Now, just run all the service using docker-compose:

```sh
docker-compose up -d
```

### Build and Run Frontend

Go to frontend folder and run 

```sh
npm install
```

Then to start run

```sh
npm run start
```

## How To Run on Kubernetes

### Build Docker Images

To build docker images for backend run following command:
```sh
mvn -pl eureka-server,service-one,service-two,reactive-service,websocket-service,gateway  -Dmaven.test.skip=true package jib:dockerBuild
```

### Build Frontend Image

To build docker image for frontend, run the following command inside `frontend` folder.
```sh
docker build -t dhananjay12/demo-frontend .
```

**Note** :- Whatever image `prefix` name you give, change in the `k8s` accordingly.

### Run on K8

```
kubectl apply -f k8s
```

## Endpoints

### Service One (REST)

**Endpoint** - `/hello`, `/headers` and `/hop/{status}`. 

If calling via gateway then - `/api/service-one/hello`

`/hop/{status}` internally calls `service-two` `/status/{status}` endpoint and the response will be that code.

### Service Two (REST)

**Endpoint** - `/hello` and `/status/{status}`

If calling via gateway then - `/api/service-two/hello`


### Reactive Service (Web Flux)

**Endpoint**  - `/greetings/sse`  and `/greetings`

If calling via gateway then - `/reactive-web-producer/greetings/sse`

### WebSocket Service  (Websocket)

**Websocker Connect Endpoint** - `/gs-guide-websocket`  
**Topic** - `/topic/greetings`
**Message Mapping** - `/hello`

If calling via gateway then - `/websocketservice/gs-guide-websocket`
