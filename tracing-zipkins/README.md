## Services

All java images have port `8080` and exposed on different ports.

Frontend - http://localhost

Eureka - http://localhost:8761/

Gateway routes - http://localhost:8080/actuator/gateway/routes

## Zipkin Dashboard

By default sending spans to Zipkin is disabled in service. We enabled it and added the server url in the common
properties of `docker-compose.yml` file. Also, we increased the sampling probability to 1 which means it will send
all the spans to Zipkins.

Dashboard - http://localhost:9411/zipkin/
