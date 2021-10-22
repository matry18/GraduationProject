package com.graduationproject.ochestrator.saga;

public interface SagaParticipator<T> {
    void transact(T oldObject, T newObject);
    void transact(T object);
    void transact(String sagaid);
    void revert(String sagaId);
}
