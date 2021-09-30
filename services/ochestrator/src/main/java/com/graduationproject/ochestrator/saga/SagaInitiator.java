package com.graduationproject.ochestrator.saga;

public interface SagaInitiator<T> {
    void beginSaga(T object);
    void revert(T object);
}
