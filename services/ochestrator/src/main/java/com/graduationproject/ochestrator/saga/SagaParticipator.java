package com.graduationproject.ochestrator.saga;

public interface SagaParticipator<T> {
    String transact(T oldObject, T newObject);
    String transact(T object);
    void transact(String sagaid);
    void revert(String sagaId);
}
