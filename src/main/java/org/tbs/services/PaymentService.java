package org.tbs.services;

import org.tbs.services.serviceInterfaces.IPaymentStrategy;

public class PaymentService implements IPaymentStrategy {
    @Override
    public boolean processPayment(Long BookingId) {
        System.out.println("Payment processed");
        return true;
    }

    @Override
    public boolean refund(Long BookingId) {
        System.out.println("refund processed");
        return true;
    }
}
