package com.graduationProject.authentication.topic;

public interface ResidentTopic {
    //CREATE RESIDENT
    static final String CreateResidentSagaBegin = "CreateResidentSagaBegin"; //used to begin the Saga (publish)
    static final String CreateResidentSagaFailed = "CreateResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String CreateResidentSagaRevert = "CreateResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String CreateResidentSagaDone = "CreateResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)

    //DELETE RESIDENT
    static final String DeleteResidentSagaInit = "DeleteResidentSagaInit";//used by the initiator service (consume)
    static final String DeleteResidentSagaBegin = "DeleteResidentSagaBegin"; //used to begin the Saga (publish)
    static final String DeleteResidentSagaFailed = "DeleteResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String DeleteResidentSagaRevert = "DeleteResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String DeleteResidentSagaDone = "DeleteResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)

    //UPDATE RESIDENT
    static final String UpdateResidentSagaInit = "UpdateResidentSagaInit";//used by the initiator service (publish)
    static final String UpdateResidentSagaBegin = "UpdateResidentSagaBegin"; //used to begin the Saga (publish)
    static final String UpdateResidentSagaFailed = "UpdateResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String UpdateResidentSagaRevert = "UpdateResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String UpdateResidentSagaDone = "UpdateResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)

}
