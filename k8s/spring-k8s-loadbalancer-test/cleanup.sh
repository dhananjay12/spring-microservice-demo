#!/bin/bash
set -xe

kubectl -n test delete -f service-account.yaml
kubectl -n test delete -f service-one-deployment.yaml
kubectl -n test delete -f service-one-service.yaml
kubectl -n test delete -f service-two-deployment.yaml
kubectl -n test delete -f service-two-service.yaml
kind delete cluster