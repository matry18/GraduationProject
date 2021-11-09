package com.graduationproject.ochestrator.topic.resident;

import com.graduationproject.ochestrator.topic.Topic;

public interface ResidentTopics extends Topic {
    //CREATE RESIDENT
    static final String CreateResidentSagaInit = "CreateResidentSagaInit";//used by the initiator service (consume)
    static final String CreateResidentSagaBegin = "CreateResidentSagaBegin"; //used to begin the Saga (publish)
    static final String CreateResidentSagaFailed = "CreateResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String CreateResidentSagaRevert = "CreateResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String CreateResidentSagaDone = "CreateResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (consume)
    static final String CreateResidentSagaInitRevert = "CreateResidentSagaInitRevert";// //used to revert only on the initiating saga.

    //DELETE RESIDENT
    static final String DeleteResidentSagaInit = "DeleteResidentSagaInit";//used by the initiator service (consume)
    static final String DeleteResidentSagaBegin = "DeleteResidentSagaBegin"; //used to begin the Saga (publish)
    static final String DeleteResidentSagaFailed = "DeleteResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String DeleteResidentSagaRevert = "DeleteResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String DeleteResidentSagaDone = "DeleteResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)
    static final String DeleteResidentSagaInitRevert = "DeleteResidentSagaInitRevert";//used to revert only on the initiating saga.

    //UPDATE RESIDENT
    static final String UpdateResidentSagaInit = "UpdateResidentSagaInit";//used by the initiator service (publish)
    static final String UpdateResidentSagaBegin = "UpdateResidentSagaBegin"; //used to begin the Saga (publish)
    static final String UpdateResidentSagaFailed = "UpdateResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String UpdateResidentSagaRevert = "UpdateResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String UpdateResidentSagaDone = "UpdateResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)
    static final String UpdateResidentSagaInitRevert = "UpdateResidentSagaInitRevert";//used to revert only on the initiating saga.
}
