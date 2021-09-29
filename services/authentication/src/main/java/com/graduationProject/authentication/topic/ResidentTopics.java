package com.graduationProject.authentication.topic;

public interface ResidentTopics {
    static final String CreateResidentSagaBegin = "CreateResidentSagaBegin"; //used to begin the Saga (publish)
    static final String CreateResidentSagaFailed = "CreateResidentSagaFailed"; //used when the orchestrator sends out revert message (consume)
    static final String CreateResidentSagaRevert = "CreateResidentSagaRevert"; //used to give the orchestrator a status of the compensating transaction(publish)
    static final String CreateResidentSagaDone = "CreateResidentSagaDone"; //used by the participating services to give the orchestrator transaction status (publish)
}
