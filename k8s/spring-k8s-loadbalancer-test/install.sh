#!/bin/bash
set -xe

kind delete cluster
kind create cluster --config kind-config.yaml
kubectl create ns test
kubectl -n test apply -f service-account.yaml
kubectl -n test apply -f service-one-deployment.yaml
kubectl -n test apply -f service-one-service.yaml
kubectl -n test apply -f service-two-deployment.yaml
kubectl -n test apply -f service-two-service.yaml


#echo "Wating for deployments to be available..."
#kubectl wait deployment -n test test-service --for condition=Available=True --timeout=90s
#kubectl wait deployment -n test reactive-service --for condition=Available=True --timeout=90s
#kubectl wait deployment -n test token-converter --for condition=Available=True --timeout=90s
#
#echo "Ready for tests..."
#echo "  run ./test.sh"