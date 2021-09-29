package com.graduationproject.bosted.saga;

public interface SagaParticipator<T> {
    void transact(T object);
    void revert(T object);
}
