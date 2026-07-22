package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Flight;

import java.time.LocalDate;
import java.util.List;

public class FlightService {
    private final EntityManagerFactory emf;

    public FlightService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Flight flight){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(flight);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Flight findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Flight.class, id);
        } finally {
            em.close();
        }
    }

    public List<Flight> findAll() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT f FROM Flight f",
                    Flight.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public void update(Long id, String origin, String destination, String airline, String flightNumber, LocalDate departureDate, LocalDate arrivalDate, String status){
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Flight flight = em.find(Flight.class, id);

            if (flight != null) {
                flight.setOrigin(origin);
                flight.setDestination(destination);
                flight.setAirline(airline);
                flight.setFlightNumber(flightNumber);
                flight.setDepartureDate(departureDate);
                flight.setArrivalDate(arrivalDate);
                flight.setStatus(status);
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
            Flight flight = em.find(Flight.class, id);
            if (flight != null) {
                em.remove(flight);
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }
}
