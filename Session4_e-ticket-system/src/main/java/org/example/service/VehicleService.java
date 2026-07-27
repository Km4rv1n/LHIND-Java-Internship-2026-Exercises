package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Vehicle;

import java.util.List;

public class VehicleService {

    private final EntityManagerFactory emf;

    public VehicleService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Vehicle vehicle) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(vehicle);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Vehicle findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(Vehicle.class, id);
        } finally {
            em.close();
        }
    }
}
