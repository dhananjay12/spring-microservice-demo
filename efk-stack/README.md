# Setup

## Install/Remove local Ingress

https://kubernetes.github.io/ingress-nginx/deploy/#using-helm

```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install local-ingress ingress-nginx/ingress-nginx
```
If you want to delete later just use `delete` afterwards.

```
helm delete local-ingress
```

## Install/Remove Services

```
kubectl apply -f services
```

For removing
```
kubectl delete -f services
```

## Install Elasticsearch

https://github.com/elastic/helm-charts/blob/master/elasticsearch/README.md

Add the elasticsearch helm repo
```
helm repo add elastic https://helm.elastic.co
```
Install with custom values
```
helm install elasticsearch elastic/elasticsearch -f efk/es_values.yaml
```

For local this could be tricky. So we override some values based on eleastic search team recommendations here
https://github.com/elastic/helm-charts/tree/master/elasticsearch/examples/docker-for-mac


## Install Kibana

https://github.com/elastic/helm-charts/blob/master/kibana/README.md

We dont need to override anything for this.
```
helm install kibana elastic/kibana
```

## Install FluentD

https://bitnami.com/stack/fluentd/helm


```
helm repo add bitnami https://charts.bitnami.com/bitnami
helm install fluentd bitnami/fluentd
```