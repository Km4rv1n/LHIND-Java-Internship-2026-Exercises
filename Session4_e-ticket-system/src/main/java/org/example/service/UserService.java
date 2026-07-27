package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.User;

import java.util.List;

public class UserService {

    private final EntityManagerFactory emf;

    public UserService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(User user) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public User findById(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
}
