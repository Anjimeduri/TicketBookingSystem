package org.tbs.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tbs.enums.BookingStatus;
import org.tbs.utils.IdGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Booking extends BaseClass {
    private Long showId;
    private Long userId;
    private LocalDate bookingDate;
    private List<Long> seats;
    private BigDecimal amount;
    private BookingStatus status;

    public Booking(Long showId, Long userId, LocalDate bookingDate, List<Long> seats, BigDecimal amount) {
        this.showId = showId;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.seats = seats;
        this.amount = amount;
        this.setId(IdGenerator.generateSequence(this.getClass()));
        log.info("created a booking entry for  -> user id {} with amount {} and booking id is {}", userId, amount, this.getId());

    }

    public void updateBookingStatus(BookingStatus status) {
        this.status = status;
    }
}
