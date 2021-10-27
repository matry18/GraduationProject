package com.graduationproject.medicineStock.saga;

public interface SagaInitiator<T> {
    void initSaga(T object);
    void initSaga(T oldObject, T newObject);
    void revert(T object, String sagaId);
}
