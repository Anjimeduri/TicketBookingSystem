package com.tbs.services.serviceInterfaces;

public interface IPaymentStrategy {
    boolean processPayment(Long BookingId);

    boolean refund(Long BookingId);
}
