package com.tbs;

import com.tbs.entity.*;
import com.tbs.enums.ShowStatus;
import com.tbs.factory.NotificationFactory;
import com.tbs.repository.*;
import com.tbs.services.*;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome to TBS!");

        UserRepository userRepository = new UserRepository();
        ShowRepository showRepository = new ShowRepository();
        EventRepository eventRepository = new EventRepository();
        VenueRepository venueRepository = new VenueRepository();
        BookingRepository bookingRepository = new BookingRepository();

        NotificationFactory notificationFactory = new NotificationFactory();
        PaymentService paymentService = new PaymentService();
        ShowService showService = new ShowService(showRepository);
        VenueService venueService = new VenueService(venueRepository);
        UserService userService = new UserService(userRepository);

        EventService eventService = new EventService(eventRepository);
        BookingService ticketBookingService = new BookingService(bookingRepository, venueService, showService, userService, notificationFactory, paymentService);

        User user1 = userService.create(
                User.builder()
                        .email("Anji")
                        .name("a@g.com")
                        .phoneNumber("12")
                        .build()
        );
        User user2 = userService.create(
                User.builder()
                        .email("Anji")
                        .name("a@g.com")
                        .phoneNumber("12")
                        .build()
        );

        Venue venue1 = venueService.create(new Venue("PVR 1", new Location(99, 100, "", 522616), true, 99, 12));
        Venue venue2 = venueService.create(new Venue("PVR 2", new Location(99, 100, "", 522616), true, 99, 12));

        Event event1 = eventService.create(
                Event.builder()
                        .name("Kalki")
                        .description("A myth entertainment")
                        .duration(3)
                        .uom("hrs")
                        .build()
        );
        Event event2 = eventService.create(
                Event.builder()
                        .name("STW")
                        .description("A myth entertainment")
                        .duration(3)
                        .uom("hrs")
                        .build()
        );

        Show show1 = showService.create(
                Show.builder()
                        .venueId(venue1.getId())
                        .eventId(event1.getId())
                        .showStatus(ShowStatus.YET_TO_START)
                        .startTime(LocalDateTime.of(2025, 06, 27, 10, 0, 0))
                        .endTime(LocalDateTime.of(2025, 06, 27, 10, 0, 0).plusHours(event1.getDuration()))
                        .showDate(LocalDateTime.of(2025, 06, 27, 10, 0, 0).toLocalDate())
                        .build()
        );
        Show show2 = showService.create(
                Show.builder()
                        .venueId(venue2.getId())
                        .eventId(event2.getId())
                        .showStatus(ShowStatus.YET_TO_START)
                        .startTime(LocalDateTime.of(2025, 06, 27, 10, 0, 0))
                        .endTime(LocalDateTime.of(2025, 06, 27, 10, 0, 0).plusHours(event1.getDuration()))
                        .showDate(LocalDateTime.of(2025, 06, 27, 10, 0, 0).toLocalDate())
                        .build()
        );

        Booking u1Booking = ticketBookingService.create(
                Booking.builder()
                        .userId(user1.getId())
                        .showId(show1.getId())
                        .bookingDate(LocalDateTime.of(2025, 06, 27, 10, 0, 0).toLocalDate())
                        .seatIds(List.of(1L, 10L))
                        .build());
        ticketBookingService.cancelBooking(u1Booking.getId());

        Booking u2Booking = ticketBookingService.create(
                Booking.builder()
                        .userId(user2.getId())
                        .showId(show2.getId())
                        .bookingDate(LocalDateTime.of(2025, 06, 27, 10, 0, 0).toLocalDate())
                        .seatIds(List.of(1L, 10L))
                        .build());
        ticketBookingService.cancelBooking(u2Booking.getId());
    }
}