package org.tbs;

import org.tbs.entity.*;
import org.tbs.enums.ShowStatus;
import org.tbs.factory.NotificationFactory;
import org.tbs.services.*;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to TBS!");
        ShowService showService = new ShowService();
        VenueService venueService = new VenueService();
        UserService userService = new UserService();
        EventService eventService = new EventService();
        NotificationFactory notificationFactory = new NotificationFactory();
        PaymentService paymentService = new PaymentService();

        BookingService ticketBookingService = new BookingService(venueService, showService, userService, notificationFactory, paymentService);
//
        User user1 = userService.register("Anji", "a@g.com", "123");
        User user2 = userService.register("Anji1", "a1@g.com", "1123");

        Venue venue1 = venueService.register("PVR", "Bangalore", 100, 99, 12);
        Venue venue2 = venueService.register("Multiplex", "Bangalore", 100, 99, 12);

        Event event1 = eventService.register("Kalki", "A myth entertainment", 3, "hrs");
        Event event2 = eventService.register("ZERO", "An AI entertainment", 3, "hrs");

        Show show1 = showService.register(
                venue1.getId(),
                event1.getId(),
                ShowStatus.YET_TO_START,
                LocalDateTime.of(2025, 06, 27, 10, 0, 0),
                LocalDateTime.of(2025, 06, 27, 10, 0, 0).plusHours(event1.getDuration()),
                LocalDateTime.of(2025, 06, 27, 10, 0, 0).toLocalDate()
        );
        Show show2 = showService.register(
                venue2.getId(),
                event2.getId(),
                ShowStatus.YET_TO_START,
                LocalDateTime.of(2025, 06, 29, 10, 0, 0),
                LocalDateTime.of(2025, 06, 29, 10, 0, 0).plusHours(event1.getDuration()),
                LocalDateTime.of(2025, 06, 29, 10, 0, 0).toLocalDate()
        );

        Booking booking = ticketBookingService.book(user1.getId(), show1.getId(), LocalDateTime.of(2025, 06, 27, 10, 0, 0).toLocalDate(), List.of(1L, 10L));
        ticketBookingService.cancelBooking(booking.getId());

        Booking u2Booking = ticketBookingService.book(user2.getId(), show2.getId(), LocalDateTime.of(2025, 06, 29, 10, 0, 0).toLocalDate(), List.of(1L, 10L));
        ticketBookingService.cancelBooking(u2Booking.getId());
    }
}