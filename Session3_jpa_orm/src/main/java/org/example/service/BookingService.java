package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Booking;
import org.example.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public class BookingService {
    private final EntityManagerFactory emf;

    public BookingService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Booking booking){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Booking findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Booking.class, id);
        } finally {
            em.close();
        }
    }

    public List<Booking> findAll() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT b FROM Booking b",
                    Booking.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public void update(Long id,LocalDate bookingDate, String status){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Booking booking = em.find(Booking.class, id);

            if (booking != null) {
                booking.setBookingDate(bookingDate);
                booking.setStatus(status);
            }

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public void delete(Long id){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, id);
            if (booking != null) {
                em.remove(booking);
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public void bookFlight(Long id, Flight flight){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, id);
            if (booking != null && flight!=null && !booking.getFlights().contains(flight)) {
                booking.getFlights().add(flight);
                flight.getBookings().add(booking);
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public void cancelFlight(Long id, Flight flight){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Booking booking = em.find(Booking.class, id);
            if (booking != null && flight!=null && booking.getFlights().remove(flight)) {
                flight.getBookings().remove(booking);
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public List<Flight> getFlights(Long bookingId) {
        EntityManager em = emf.createEntityManager();

        try {
            Booking booking = em.find(Booking.class, bookingId);

            if (booking != null) {
                return booking.getFlights();
            }

            return List.of();

        } finally {
            em.close();
        }
    }
}
