package org.example;

import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Booking;
import org.example.entity.Flight;
import org.example.entity.User;
import org.example.entity.UserDetails;
import org.example.service.BookingService;
import org.example.service.FlightService;
import org.example.service.UserService;
import org.example.util.JpaUtil;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

        UserService userService = new UserService(emf);
        BookingService bookingService = new BookingService(emf);
        FlightService flightService = new FlightService(emf);

        System.out.println("Connected successfully!");

        // Create user
        User user = new User(
                "user1",
                "password1",
                "USER"
        );

        userService.save(user);


        // Retrieve user
        User savedUser = userService.findById(1L);

        System.out.println(savedUser);


        // Update user
        userService.update(
                1L,
                "updatedUser",
                "newPassword",
                "ADMIN"
        );


        // Add user details
        UserDetails details = new UserDetails(
                "John",
                "Smith",
                "john@email.com",
                "12345678"
        );

        userService.addUserDetails(1L, details);


        // Create booking
        Booking booking = new Booking(
                LocalDate.now(),
                "CONFIRMED"
        );

        bookingService.save(booking);


        // Connect booking with user
        userService.addBooking(
                1L,
                1L
        );


        // Create flight
        Flight flight = new Flight(
                "London",
                "Paris",
                "Airline",
                "FL123",
                LocalDate.now(),
                LocalDate.now(),
                "AVAILABLE"
        );

        flightService.save(flight);


        // Connect flight with booking
        bookingService.bookFlight(
                1L,
                flight
        );


        Booking savedBooking = bookingService.findById(1L);

        System.out.println(savedBooking);


        // userService.delete(1L);

        JpaUtil.close();
    }
}
