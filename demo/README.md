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
