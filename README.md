# GraduationProject
Graduation Project with focus on micro services and how to synchronize between those
To run with k8s in Minikube:
shortcuts:
k = kubectl
m = minikube

To start minikube:
$ m start --driver=docker

To start tunnel for LoadBalancer
$ m tunnel (i egen terminal)

To operate on containers inside Minikube
$ eval $(minikube docker-env)

To start Bosted DB, go to /DB/
$ k apply -f bosted-db-deployment.yaml

To start the backend for Bosted, go to root
$ k apply -f bosted-deployment.yaml

To get the URL for the frontend
$ m service bosted --url

Copy and paste the URL:port as the bostedApi-variable in the environment.ts-file in frontend/frontend/src/environments/environment.ts) and perhaps also: /frontend/frontend/src/environments/environment.prod.ts

Copy and paste the URL:port into the nginx.conf-file in /frontend/frontend/

Delete the /dist-folder

Build the frontend in /frontend/frontend/
$ ng build

Build the docker image in /frontend/frontend/
$ docker build -t frontend .

To deploy the frontend got to root
$ k apply -f frontend-deployment.yaml

EOF

---
To run as local host:

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
