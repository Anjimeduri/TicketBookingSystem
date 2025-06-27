package org.tbs.services.serviceInterfaces;

import org.tbs.entity.Booking;

public interface IBookingService extends IBaseService<Booking> {
    void cancelBooking(Long bookingId);
}
