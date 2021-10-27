package com.graduationproject.medicineStock.saga;

public interface SagaParticipator<T> {
    void transact(T object);
    void revert(T object);
}
