# GraduationProject
Graduation Project with focus on micro services and how to synchronize between those

To create a target folder:

Go to: /demo
run $ mvn clean install
To build and run backend:

Go to /target
run $ docker build -t backend-demo .
run $ docker run -d -p 8080:8080
To build the frontend:

Go to /frontend
run $ng build --prod
To build and run frontend:

Go to /frontend
run $ docker build -t frontend-demo .
run $ docker run -d -p 1324:80 frontend-demo

To run kafka with zookeeper:

Stand in the folder (/demo) with the docker-compose.yml file
run $ docker-compose up (you can use the -d flag to run in detached mode)
Things to know about kafka:

In de docker-compose.yml file the topics are defined (but don't have to be)
Each topic name is defined in the Producer class
Topics and group_id are defined in the Comsumer class

# **run frontend with k8s**
$ m start --vm-driver=docker

# check minikube status
$ m status

# check k8s version
$ k version

# set docker env to connect to minikube
eval $(minikube docker-env)

# check that docker containers are running in minikube
$ docker ps

# check status with nodes and pods
m kubectl -- get pods -A -o wide

# check nodes
$ k get nodes

# cd to dockerfile to build
$ docker build -t frontend .
(build tag is the same as the label in the frontend-deployment.yml-file)

# cd to frontend-deployment.yml to apply and run service
$ k apply -f frontend-deployment.yml
$ m service frontend-service 
(app-name is the same as service name in frontend-deployment.yml-file)

# Additional commands
# see UI for k8s in browser
$ minikube dashboard

# delete minikube
minikube delete
