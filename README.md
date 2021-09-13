# GraduationProject
Graduation Project with focus on micro services and how to synchronize between those

To create a target folder:
- Go to: /demo
- run $ mvn clean install

To build and run backend:
- Go to /target
- run $ docker build -t backend-demo .
- run $ docker run -d -p 8080:8080

To build the frontend:
- Go to /frontend
- run $ng build --prod

To build and run frontend:
- Go to /frontend
- run $ docker build -t frontend-demo .
- run $ docker run -d -p 1324:80 frontend-demo 

