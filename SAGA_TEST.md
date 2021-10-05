Overall Context:
Create Resident

Context:
Create a new Resident successfully

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser

Expected outcome:
- User is created and saved on the Bosted DB
- User is created and saved on the Autentication DB
- User is deleted from the Orchestrator DB

---

Context:
Creating a new user and Authentication returns FAILURE -> the user is not created.

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser
- Set username: fail (a service throws an exception)

Expected outcome:
- The user is saved on Bosted (the service types that does not fail)
- The user is saved on the Orchestrator
- The Authentication services fails
- The user is NOT created on the Autehntication DB
-> Then:
- A compensating transaction will be triggered
- The user is deleted from the Bosted DB
- The user is deleted from the Authentication DB
- The user is deleted from the Orchestrator DB

---

Context:
Creating a new user and Authentication returns FAILURE -> the user is not created and the revert fails and has to be manually handled by a developer.

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser
- Set username: fail (a service throws an exception)
- Set password: fail (a service throws an exception)

Expected outcome:
- The user is saved on Bosted (the service types that does not fail)
- The user is saved on the Orchestrator
- The Authentication services fails
- The user is created on the Autehntication DB
-> Then:
- A compensating transaction will be triggered
- The revert fails
-> Then:
- The user is deleted from the Bosted DB
- The user is kept on the failed service (Authentication)
- The user is not deleted from the Orchestrator DB

---

Context:
Create a new user while one service (Authentication) is down. The Orchestrator should keep the message until the service (Authentication) is up and running again.

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is NOT running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser
- Spin up the service (Authentication)

Expected outcome:
- The user is created and saved on the Bosted DB
- The user is created on the Orchestrator
-> Then:
- Spin up Authentication
- User is created and saved on the Autentication DB
- The user is deleted from the Orchestrator DB

---

Context:
Create a new user while one service (Authentication) is down. The service (Authentication) spins up and fails the creation of the user.

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is NOT running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser
- Set username: fail (a service throws an exception)
- Spin up the service (Authentication)

Expected outcome:
- The user is created and saved on the Bosted DB
- The user is created on the Orchestrator
-> Then:
- Spin up Authentication
- The user is not created on the Autentication DB
- The user is deleted from the Orchestrator DB
- The user is deleted from the Bosted DB

---

Context:
Create a new user while one service (Authentication) is down. The service (Authentication) spins up and fails the creation of the user. The revert fails as well.

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is NOT running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser
- Set username: fail (a service throws an exception)
- Set password: fail (a service throws an exception)
- Spin up the service (Authentication)

Expected outcome:
- The user is created and saved on the Bosted DB
- The user is created on the Orchestrator
-> Then:
- Spin up Authentication
- The user is deleted from the Bosted DB
- The user is kept on the failed service (Authentication)
- The user is not deleted from the Orchestrator DB



Overall Context:
Delete Resident


Context:
Delete a Resident successfully

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- delete a resident from the frontend UI in the browser

Expected outcome:
- User is deleted from the Bosted DB
- User is deleted from the Autentication DB
- Useris deleted from the Orchestrator DB

---
Context:
Deleting a resident and Authentication returns FAILURE -> the resident is not deleted

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Delete a resident from the frontend UI in the browser
- Set username: neverDelete (a service throws an exception)

Expected outcome:
- The user  is deleted from the Bosted DB (the service types that does not fail)
- The user  is deleted from the Bosted DB
- The Authentication services fails
- The user is NOT deleted on the Autehntication DB
-> Then:
- A compensating transaction will be triggered
- The user is created from the Bosted DB
- The user is created from the Authentication DB
- The user is deleted from the Orchestrator DB (As the Saga is done)

---
Context:
Deleting a resident and Authentication returns FAILURE -> the user is not deleted and the revert fails and has to be manually handled by a developer.

Pre req.:
- Kafka is running
- Bosted is running
- Authentication is running
- Orchestrator is running
- DB is running
- Frontend is running

Steps:
- Create a new resident from the frontend UI in the browser
- Set username: neverDelete(a service throws an exception)
- Set password: neverDelete(a service throws an exception on the revert flow)

Expected outcome:
- The user is deleted on Bosted (the service types that does not fail)
- The user is deleted on the Orchestrator
- The Authentication services fails
- The user is deleted on the Autehntication DB
-> Then:
- A compensating transaction will be triggered
- The revert fails
-> Then:
- The user is created on the Bosted DB
- The user is not created on the failed service (Authentication)
- The user is not deleted from the Orchestrator DB (as it should keep the resident instance copy until at developer manually handles the error)

---
