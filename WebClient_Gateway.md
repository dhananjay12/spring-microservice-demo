## Build Gateway
Go to gateway folder and build the image
```
 mvn clean install -Dmaven.test.skip=true
 mvn clean package jib:dockerBuild -Dmaven.test.skip=true -Djib.to.tags=webclient1
```

This would create a docker image locally - dhananjay12/demo-gateway:webclient1

## Docker Compose

Go to tracing-zipkins folder and run the below command
```
docker-compose up -d
```
The gateway is configured to use the extra filter via env variables

## Test

Eureka - http://localhost:8761/ - Gateway and 2 services 
Zipkin - http://localhost:9411/zipkin/ - Tracing

Gateway - http://localhost:8080/api/service-two/headers will first make a call to service-one via the filter, add its
response to the request and pass it service-two. The overall response from service-two will be the headers its received. 

