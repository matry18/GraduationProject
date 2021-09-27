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