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

To delete minikube:
$ m delete

To set the environment for minikube (use one terminal)
eval $(minikube docker-env)

To see running cluster
$ kubectl get po -A

To get UI dashboard through the browser
$ minikube dashboard

-----------
$ m start --vm-driver=docker

check status on nodes:
$ k get nodes
or $ m kubectl -- get pods -A -o wide

check status on minikube:
$ m status

check kubernetes verison:
$ k version
Make sure that you see Client Version and Server Version to have K8s korrect installed

Minikube is only to start, stop / delete a cluster. Everything else is through kubectl
