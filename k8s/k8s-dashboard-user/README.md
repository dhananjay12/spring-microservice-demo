## User for K8s dashboard

If you are following https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/ you will also 
need a user to access the dashboard. While its linked in the doc, install these yaml.

https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md

This is a one time activity if your K8s cluster hasnt changed. 

```
kubectl apply -f k8s-dashboard-user

kubectl -n kubernetes-dashboard create token admin-user
```

To open dashboard 
```
kubectl proxy

```

http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/#/login