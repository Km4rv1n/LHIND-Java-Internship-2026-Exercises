package org.example;

import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Ticket;
import org.example.entity.User;
import org.example.entity.Vehicle;
import org.example.enums.UserRole;
import org.example.service.TicketService;
import org.example.service.UserService;
import org.example.service.VehicleService;
import org.example.util.JpaUtil;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();

        UserService userService = new UserService(emf);
        VehicleService vehicleService = new VehicleService(emf);
        TicketService ticketService = new TicketService(emf);


        // -------------------------
        // Create users
        // -------------------------

        User citizen = new User();
        citizen.setFirstName("John");
        citizen.setLastName("Smith");
        citizen.setRole(UserRole.CITIZEN);

        userService.save(citizen);


        User cop = new User();
        cop.setFirstName("Mike");
        cop.setLastName("Johnson");
        cop.setRole(UserRole.COP);

        userService.save(cop);


        System.out.println("Citizen id: " + citizen.getId());
        System.out.println("Cop id: " + cop.getId());


        // -------------------------
        // Create vehicle
        // -------------------------

        Vehicle vehicle = new Vehicle();

        vehicle.setChassisNumber("ABC123456");
        vehicle.setPlateNumber("XYZ-999");
        vehicle.setOwnedBy(citizen);

        // keep both sides synchronized
        citizen.getOwnedVehicles().add(vehicle);

        vehicleService.save(vehicle);


        System.out.println("Vehicle id: " + vehicle.getId());


        // -------------------------
        // Issue ticket
        // -------------------------

        Ticket ticket = ticketService.issueTicket(
                cop.getId(),
                vehicle.getId(),
                new BigDecimal("100.00"),
                "Speeding above the limit"
        );

        System.out.println(
                "Ticket created: " + ticket.getId()
        );

        System.out.println(
                "Ticket status: " + ticket.getStatus()
        );


        // -------------------------
        // Pay ticket
        // -------------------------

        Ticket paidTicket = ticketService.payTicket(
                ticket.getId(),
                citizen.getId()
        );

        System.out.println(
                "Ticket after payment: " + paidTicket.getStatus()
        );


        // -------------------------
        // Refund ticket
        // -------------------------

        Ticket refundedTicket = ticketService.refundTicket(
                cop.getId(),
                ticket.getId()
        );

        System.out.println(
                "Ticket after refund: " + refundedTicket.getStatus()
        );


        // -------------------------
        // Create another ticket
        // to test cancellation
        // -------------------------

        Ticket secondTicket = ticketService.issueTicket(
                cop.getId(),
                vehicle.getId(),
                new BigDecimal("50.00"),
                "Illegal parking"
        );

        System.out.println(
                "Second ticket status: " + secondTicket.getStatus()
        );


        Ticket cancelledTicket = ticketService.cancelTicket(
                cop.getId(),
                secondTicket.getId()
        );

        System.out.println(
                "Second ticket after cancellation: "
                        + cancelledTicket.getStatus()
        );


        emf.close();
    }
}