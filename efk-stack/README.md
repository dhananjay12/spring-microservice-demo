# Setup

## Install/Remove local Ingress

https://kubernetes.github.io/ingress-nginx/deploy/

```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.41.2/deploy/static/provider/cloud/deploy.yaml
```
If you want to delete later just use `delete` afterwards.

```
kubectl delete -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v0.41.2/deploy/static/provider/cloud/deploy.yaml
```

## Install/Remove Services

```
kubectl apply -f services
```

For removing
```
kubectl delete -f services
```