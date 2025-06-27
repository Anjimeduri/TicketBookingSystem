package com.tbs.services;

import com.tbs.entity.*;
import com.tbs.enums.BookingStatus;
import com.tbs.enums.NotificationType;
import com.tbs.enums.SeatStatus;
import com.tbs.exceptions.NotAvailableException;
import com.tbs.exceptions.PaymentFailedException;
import com.tbs.factory.NotificationFactory;
import com.tbs.repository.BookingRepository;
import com.tbs.services.serviceInterfaces.IBookingService;
import com.tbs.services.serviceInterfaces.IShowService;
import com.tbs.services.serviceInterfaces.IUserService;
import com.tbs.services.serviceInterfaces.IVenueService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class BookingService extends BaseService<Booking> implements IBookingService {
    private final NotificationFactory notificationFactory;

    // Dependency on abstraction class and not on concrete.This gives easy way to switch to diff implementation
    private final IVenueService venueService;
    private final IShowService showService;
    private final IUserService userService;
    private final PaymentService paymentService;

    public BookingService(BookingRepository bookingRepository, IVenueService venueService, IShowService showService,
                          IUserService userService, NotificationFactory notificationFactory, PaymentService paymentService) {
        super(bookingRepository);
        this.venueService = venueService;
        this.showService = showService;
        this.userService = userService;
        this.notificationFactory = notificationFactory;
        this.paymentService = paymentService;
    }

    @Override
    public synchronized Booking create(Booking request) {
        Objects.requireNonNull(request, "request object cant be empty");
        Objects.requireNonNull(request.getSeatIds());
        if (request.getSeatIds().isEmpty()) {
            log.error("At least one seat should be selected to book");
            throw new IllegalArgumentException("At least one seat should be selected to book");
        }

        User user = userService.findById(request.getUserId());
        Show show = showService.findById(request.getShowId());
        Venue venue = venueService.findById(show.getVenueId());

        Booking booking = processBooking(user, show, venue, request.getBookingDate(), request.getSeatIds());
        return finalizeBooking(booking, request.getUserId());
    }

    public synchronized void cancelBooking(Long bookingId) {
        log.info("Cancelling booking {}", bookingId);
        Optional<Booking> booking = getRepository().findById(bookingId);
        if (booking.isEmpty()) {
            throw new IllegalArgumentException("Booking id not found");
        }
        booking.get().updateBookingStatus(BookingStatus.CANCELLED);
        processBookingCancellation(booking.get());
    }

    private synchronized Booking processBooking(User user, Show show, Venue venue, LocalDate bookingDate, List<Long> seatIds) {
        Map<Long, Seat> venueSeatMapping = venue.getSeatMap();
        BigDecimal amount = BigDecimal.ZERO;

        for (Long seatId : seatIds) {
            Seat seat = venueSeatMapping.get(seatId);
            if (seat.getStatus() != SeatStatus.AVAILABLE) {
                throw new NotAvailableException("Sorry, this seat has already been booked. Please try other seats.");
            }
            seat.setStatus(SeatStatus.BLOCKED);
            amount = amount.add(seat.getPrice());
        }
        log.info("Creating a booking entry for user id {} with amount {}", user.getId(), amount);

        Booking bookingDetails = Booking.builder()
                .userId(user.getId())
                .showId(show.getId())
                .bookingDate(bookingDate)
                .seatIds(seatIds)
                .amount(amount)
                .status(BookingStatus.RESERVED)
                .build();
        Booking booking = getRepository().save(bookingDetails);
        log.info("created a booking entry for  -> user id {} with amount {} and booking id is {}", booking.getUserId(), amount, booking.getId());
        return booking;
    }

    private void processBookingCancellation(Booking booking) {
        updateSeatStatus(booking.getShowId(), booking.getSeatIds(), SeatStatus.AVAILABLE);
        log.info("Seats updated to available");
        getRepository().delete(booking.getId());
        notificationFactory.sendNotification(NotificationType.SMS, booking.getUserId());
        notificationFactory.sendNotification(NotificationType.EMAIL, booking.getUserId());
    }

    private Booking finalizeBooking(Booking booking, Long userId) {
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

    private void confirmBooking(Booking booking) {
        booking.updateBookingStatus(BookingStatus.SUCCESS);
        getRepository().update(booking);
        updateSeatStatus(booking.getShowId(), booking.getSeatIds(), SeatStatus.BOOKED);

    }

    private void rollBackBooking(Booking booking) {
        booking.updateBookingStatus(BookingStatus.FAILED);
        getRepository().delete(booking.getId());
        updateSeatStatus(booking.getShowId(), booking.getSeatIds(), SeatStatus.AVAILABLE);
    }

    private void updateSeatStatus(Long showId, List<Long> seatIds, SeatStatus seatStatus) {
        Show show = showService.findById(showId);
        Venue venue = venueService.findById(show.getVenueId());
        Map<Long, Seat> seatMapping = venue.getSeatMap();
        seatIds.forEach(seatId -> {
            seatMapping.get(seatId).setStatus(seatStatus);
        });
    }
}
