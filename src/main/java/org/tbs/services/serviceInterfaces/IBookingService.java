package org.tbs.services.serviceInterfaces;

import org.tbs.entity.Booking;

import java.time.LocalDate;
import java.util.List;

public interface IBookingService {
    Booking book(Long userId, Long showId, LocalDate bookingDate, List<Long> seatIds);

    void cancelBooking(Long bookingId);

    void updateBooking();

    Booking findById(Long id);
}
