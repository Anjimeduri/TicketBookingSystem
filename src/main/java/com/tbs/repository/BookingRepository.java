package com.tbs.repository;

import com.tbs.entity.Booking;
import com.tbs.enums.BookingStatus;

import java.util.List;

public class BookingRepository extends BaseRepository<Booking> {
    public List<Booking> findAllByIdsInAndStatus(List<Long> ids, BookingStatus status) {
        List<Booking> bookings = this.findAll();
        return bookings.stream()
                .filter(booking -> ids.contains(booking.getId()))
                .filter(booking -> booking.getStatus().equals(status))
                .toList();
    }
}
