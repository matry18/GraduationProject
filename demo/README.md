K8s

To start minikube:
$ m start --nodes=2

To get pods:
$ k get pods

To get services
$ k get svc

To apply changes from deployment.yml
$ k apply -f deployment.yml

To run the service
$ m service myapp
where myapp is the name of the application


-----
To remove a service:
$ kubectl get service -o wide
$ kubectl delete svc <YourServiceName>

To remove a pod:
$ kubectl get deployments --all-namespaces
$ kubectl delete -n NAMESPACE deployment DEPLOYMENT
Where NAMESPACE is the namespace it's in, and DEPLOYMENT is the name of the deployment. If NAMESPACE is default, leave off the -n option altogether.
E.g. $ k delete deployment myapp
