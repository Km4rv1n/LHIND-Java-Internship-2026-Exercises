package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Ticket;
import org.example.entity.User;
import org.example.entity.Vehicle;
import org.example.enums.TicketStatus;
import org.example.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TicketService {

    private final EntityManagerFactory emf;

    public TicketService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Ticket ticket) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(ticket);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Ticket findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

    public Ticket issueTicket(Long officerId, Long vehicleId, BigDecimal price, String description){

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User officer = em.find(User.class, officerId);

            if (officer == null) {
                throw new RuntimeException("Officer not found.");
            }

            if (officer.getRole() != UserRole.COP) {
                throw new RuntimeException("Only officers can issue tickets.");
            }

            Vehicle vehicle = em.find(Vehicle.class, vehicleId);

            if (vehicle == null) {
                throw new RuntimeException("Vehicle not found.");
            }

            Ticket ticket = new Ticket();

            ticket.setIssuedBy(officer);
            ticket.setVehicle(vehicle);

            ticket.setPrice(price);
            ticket.setDescription(description);

            ticket.setIssueDate(LocalDateTime.now());
            ticket.setStatus(TicketStatus.CREATED);

            officer.getIssuedTickets().add(ticket);
            vehicle.getTickets().add(ticket);

            em.persist(ticket);
            em.getTransaction().commit();
            return ticket;
        }
        catch (Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            em.close();
        }
    }

    public Ticket payTicket(Long ticketId, Long userId) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Ticket ticket = em.find(Ticket.class, ticketId);

            if (ticket == null) {
                throw new RuntimeException("Ticket not found.");
            }

            if (ticket.getStatus() != TicketStatus.CREATED) {
                throw new RuntimeException("Only created tickets can be paid.");
            }

            if(!ticket.getVehicle().getOwnedBy().getId().equals(userId)){
                throw new RuntimeException("You cannot pay this ticket.");
            }

            ticket.setStatus(TicketStatus.PAID);
            ticket.setDatePaid(LocalDateTime.now());

            em.getTransaction().commit();

            return ticket;
        }
        catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        finally {
            em.close();
        }
    }

    public Ticket refundTicket(Long copId, Long ticketId) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            User cop = em.find(User.class, copId);

            if (cop == null) {
                throw new RuntimeException("User not found.");
            }

            if (cop.getRole() != UserRole.COP) {
                throw new RuntimeException("Only cops can refund tickets.");
            }

            Ticket ticket = em.find(Ticket.class, ticketId);

            if (ticket == null) {
                throw new RuntimeException("Ticket not found.");
            }

            if (ticket.getStatus() != TicketStatus.PAID) {
                throw new RuntimeException("Only paid tickets can be refunded.");
            }

            ticket.setStatus(TicketStatus.REFUNDED);

            em.getTransaction().commit();

            return ticket;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }

    public Ticket cancelTicket(Long copId, Long ticketId) {

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            User cop = em.find(User.class, copId);

            if (cop == null) {
                throw new RuntimeException("User not found.");
            }

            if (cop.getRole() != UserRole.COP) {
                throw new RuntimeException("Only cops can cancel tickets.");
            }

            Ticket ticket = em.find(Ticket.class, ticketId);

            if (ticket == null) {
                throw new RuntimeException("Ticket not found.");
            }

            if (ticket.getStatus() != TicketStatus.CREATED) {
                throw new RuntimeException("Only created tickets can be cancelled.");
            }

            ticket.setStatus(TicketStatus.CANCELLED);

            em.getTransaction().commit();

            return ticket;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;

        } finally {
            em.close();
        }
    }
}
