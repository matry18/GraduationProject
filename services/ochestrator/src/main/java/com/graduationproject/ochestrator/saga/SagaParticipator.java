package com.graduationproject.ochestrator.saga;

public interface SagaParticipator<T> {
    void transact(T object);
    void revert(String sagaId);
}
