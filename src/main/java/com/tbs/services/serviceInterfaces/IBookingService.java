package com.tbs.services.serviceInterfaces;

import com.tbs.entity.Booking;

public interface IBookingService extends IBaseService<Booking> {
    void cancelBooking(Long bookingId);
}
