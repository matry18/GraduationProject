package com.graduationproject.bosted.topic;

public interface EmployeeTopics {
    //CREATE EMPLOYEE
    static final String CreateEmployeeSagaInit = "CreateEmployeeSagaInit";//used by the initiator service (publish)
    static final String CreateEmployeeSagaBegin = "CreateEmployeeSagaBegin"; //used to begin the Saga by the orchestrator(consume)
    static final String CreateEmployeeSagaFailed = "CreateEmployeeSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String CreateEmployeeSagaRevert = "CreateEmployeeSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String CreateEmployeeSagaDone = "CreateEmployeeSagaDone"; //used by the participating services to give the orchestrator transaction status (consume)
    static final String CreateEmployeeSagaInitRevert = "CreateEmployeeSagaInitRevert"; //used to revert only on the initiating saga.

    //DELETE EMPLOYEE
    static final String DeleteEmployeeSagaInit = "DeleteEmployeeSagaInit";//used by the initiator service (publish)
    static final String DeleteEmployeeSagaBegin = "DeleteEmployeeSagaBegin"; //used to begin the Saga (publish)
    static final String DeleteEmployeeSagaFailed = "DeleteEmployeeSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String DeleteEmployeeSagaRevert = "DeleteEmployeeSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String DeleteEmployeeSagaDone = "DeleteEmployeeSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)
    static final String DeleteEmployeeSagaInitRevert = "DeleteEmployeeSagaInitRevert";//used to revert only on the initiating saga.

    //UPDATE EMPLOYEE
    static final String UpdateEmployeeSagaInit = "UpdateEmployeeSagaInit";//used by the initiator service (publish)
    static final String UpdateEmployeeSagaBegin = "UpdateEmployeeSagaBegin"; //used to begin the Saga (publish)
    static final String UpdateEmployeeSagaFailed = "UpdateEmployeeSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String UpdateEmployeeSagaRevert = "UpdateEmployeeSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String UpdateEmployeeSagaDone = "UpdateEmployeeSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)
    static final String UpdateEmployeeSagaInitRevert = "UpdateEmployeeSagaInitRevert";//used to revert only on the initiating saga.

}
