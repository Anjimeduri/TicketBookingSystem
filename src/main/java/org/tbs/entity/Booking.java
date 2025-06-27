package org.tbs.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.tbs.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Getter
@Setter
@Builder
public class Booking extends BaseClass {
    private Long showId;
    private Long userId;
    private LocalDate bookingDate;
    private List<Long> seatIds;
    private BigDecimal amount;
    private BookingStatus status;

    public void updateBookingStatus(BookingStatus status) {
        this.status = status;
    }
}
