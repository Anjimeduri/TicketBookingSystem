package org.tbs.entity;

import lombok.Getter;
import lombok.Setter;
import org.tbs.enums.SeatType;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Venue extends BaseClass {
    private String name;
    private Location location;
    private Boolean isActive;
    private Map<Long, Seat> seatMap = new HashMap<>();
    private final AtomicLong seatSequence = new AtomicLong(0);

    public Venue(String name, Location location, Boolean isActive, Integer rows, Integer cols) {
        this.name = name;
        this.location = location;
        this.isActive = isActive;
        registerSeats(rows, cols);
    }

    private void registerSeats(int rows, int cols) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                SeatType seatType = row <= 2 ? SeatType.GENERAL : SeatType.PREMIUM;
                BigDecimal price = seatType == SeatType.GENERAL ? BigDecimal.valueOf(100) : BigDecimal.valueOf(150);
                Seat seat = new Seat(seatSequence.incrementAndGet(), this.getId(), seatType, price, row, col);
                seatMap.put(seat.getId(), seat);
            }
        }
    }

    public void updateVenueStatus(Boolean status) {
        this.setIsActive(status);
    }

    public Boolean isVenueActive() {
        return this.isActive;
    }
}
