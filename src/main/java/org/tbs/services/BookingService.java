package org.tbs.services;

import lombok.extern.slf4j.Slf4j;
import org.tbs.entity.*;
import org.tbs.enums.BookingStatus;
import org.tbs.enums.NotificationType;
import org.tbs.enums.SeatStatus;
import org.tbs.exceptions.NotAvailableException;
import org.tbs.exceptions.PaymentFailedException;
import org.tbs.factory.NotificationFactory;
import org.tbs.services.serviceInterfaces.IBookingService;
import org.tbs.services.serviceInterfaces.IShowService;
import org.tbs.services.serviceInterfaces.IUserService;
import org.tbs.services.serviceInterfaces.IVenueService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class BookingService implements IBookingService {
    private final NotificationFactory notificationFactory;
    // Dependency on abstraction not on concrete.This gives easy way to switch to diff implementation
    private final IVenueService venueService;
    private final IShowService showService;
    private final IUserService userService;
    private final PaymentService paymentService;
    private final Map<Long, Booking> bookingMap = new ConcurrentHashMap<>();

    public BookingService(IVenueService venueService, IShowService showService, IUserService userService, NotificationFactory notificationFactory, PaymentService paymentService) {
        this.venueService = venueService;
        this.showService = showService;
        this.userService = userService;
        this.notificationFactory = notificationFactory;
        this.paymentService = paymentService;
    }

    @Override
    public synchronized Booking book(Long userId, Long showId, LocalDate bookingDate, List<Long> seatIds) {
        Objects.requireNonNull(seatIds);
        if (seatIds.isEmpty()) {
            log.error("At least one seat should be selected to book");
            throw new IllegalArgumentException("At least one seat should be selected to book");
        }

        User user = userService.findById(userId);
        Show show = showService.findById(showId);
        Venue venue = venueService.findById(show.getVenueId());

        Booking booking = processBooking(user, show, venue, bookingDate, seatIds);
        return finalizeBooking(booking, userId);
    }

    @Override
    public synchronized void cancelBooking(Long bookingId) {
        log.info("Cancelling booking {}", bookingId);
        if (!bookingMap.containsKey(bookingId)) {
            throw new IllegalArgumentException("Booking id not found");
        }
        Booking booking = bookingMap.get(bookingId);
        booking.updateBookingStatus(BookingStatus.CANCELLED);
        processBookingCancellation(booking);
    }

    @Override
    public void updateBooking() {

    }

    @Override
    public Booking findById(Long id) {
        if (!bookingMap.containsKey(id)) {
            throw new IllegalArgumentException("Booking not found with the provided ID");
        }
        return bookingMap.get(id);
    }

    public synchronized Booking processBooking(User user, Show show, Venue venue, LocalDate bookingDate, List<Long> seatIds) {
        Map<Long, Seat> venueSeatMapping = venue.getSeatMap();
        BigDecimal amount = BigDecimal.ZERO;

        seatIds.forEach(seatId -> {
            Seat seat = venueSeatMapping.get(seatId);
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new NotAvailableException("Sorry this seat has been booked already please try some other seats");
            }
            seat.setStatus(SeatStatus.BLOCKED);
            amount.add(seat.getPrice());
        });
        log.info("Creating a booking entry for  -> user id {} with amount {}", user.getId(), amount);

        return new Booking(show.getId(), user.getId(), bookingDate, seatIds, amount);
    }

    public void processBookingCancellation(Booking booking) {
        updateSeatStatus(booking.getShowId(), booking.getSeats(), SeatStatus.AVAILABLE);
        log.info("Seats updated to available");
        bookingMap.remove(booking.getId());
        notificationFactory.sendNotification(NotificationType.SMS, booking.getUserId());
        notificationFactory.sendNotification(NotificationType.EMAIL, booking.getUserId());
    }

    public Booking finalizeBooking(Booking booking, Long userId) {
        try {
            boolean processed = paymentService.processPayment(booking.getId());
            if (processed) {
                confirmBooking(booking);
                notificationFactory.sendNotification(NotificationType.SMS, userId);
                notificationFactory.sendNotification(NotificationType.EMAIL, userId);
                return booking;
            } else {
                rollBackBooking(booking);
                throw new PaymentFailedException("Payment failed");
            }
        } catch (Exception e) {
            rollBackBooking(booking);
            throw e;
        }
    }

    public void confirmBooking(Booking booking) {
        booking.updateBookingStatus(BookingStatus.SUCCESS);
        bookingMap.put(booking.getId(), booking);
        updateSeatStatus(booking.getShowId(), booking.getSeats(), SeatStatus.BOOKED);

    }

    public void rollBackBooking(Booking booking) {
        booking.updateBookingStatus(BookingStatus.FAILED);
        bookingMap.remove(booking.getId());
        updateSeatStatus(booking.getShowId(), booking.getSeats(), SeatStatus.AVAILABLE);
    }

    public void updateSeatStatus(Long showId, List<Long> seatIds, SeatStatus seatStatus) {
        Show show = showService.findById(showId);
        Venue venue = venueService.findById(show.getVenueId());
        Map<Long, Seat> seatMapping = venue.getSeatMap();
        seatIds.forEach(seatId -> {
            seatMapping.get(seatId).setStatus(seatStatus);
        });
    }
}
