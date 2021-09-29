package com.graduationproject.bosted.saga;

public interface SagaInitiator<T> {
    void beginSaga(T object);
    void revert(T object, String sagaId);
}
