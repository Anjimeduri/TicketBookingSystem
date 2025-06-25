package org.tbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.tbs.enums.SeatStatus;
import org.tbs.enums.SeatType;

import java.math.BigDecimal;

@Getter
@Setter
public class Seat extends BaseClass {
    private Long venueId;
    private SeatType seatType;
    private BigDecimal price;
    private Integer row;
    private Integer col;
    private SeatStatus status;

    public Seat(Long seatId, Long venueId, SeatType seatType, BigDecimal price, Integer row, Integer col) {
        this.venueId = venueId;
        this.seatType = seatType;
        this.price = price;
        this.row = row;
        this.col = col;
        this.status = SeatStatus.AVAILABLE;
        this.setId(seatId);
    }
}
