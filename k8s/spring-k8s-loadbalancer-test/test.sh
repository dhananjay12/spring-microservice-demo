#!/bin/bash
kubectl port-forward -n test deployment/service-one 38080:8080 2>/dev/null &
sleep 3

docker exec kind-control-plane tail -n0 -F /var/log/kubernetes/kube-apiserver-audit.log &

for ((i=0;i<10;i++));
do
  printf ">> Attempting curl request: %s \n" "$i"
  curl -s -o /dev/null -w "<<< Curl reponse code: %{http_code} \n\n\n"  http://localhost:38080/hop/headers
  sleep 1
done

sleep 1
pkill -P $$