package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Booking;
import org.example.entity.User;
import org.example.entity.UserDetails;

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


    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT u FROM User u",
                    User.class
            ).getResultList();

        } finally {
            em.close();
        }
    }


    public void update(Long id, String username, String password, String role) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            User user = em.find(User.class, id);

            if (user != null) {
                user.setUsername(username);
                user.setPassword(password);
                user.setRole(role);
            }

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }


    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }


    public void addBooking(Long userId, Long bookingId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            User user = em.find(User.class, userId);
            Booking booking = em.find(Booking.class, bookingId);

            if (user != null && booking != null) {
                user.getBookings().add(booking);
                booking.setUser(user);
            }

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public void removeBooking(Long userId, Long bookingId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            Booking booking = em.find(Booking.class, bookingId);

            if (user != null && booking !=null) {
                user.getBookings().remove(booking);
                booking.setUser(null);
            }

            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public void addUserDetails(Long userId, UserDetails userDetails) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);

            if (user != null) {
                user.setUserDetails(userDetails);
                userDetails.setUser(user);
            }
            em.getTransaction().commit();

        } finally {
            em.close();
        }
    }

    public UserDetails getUserDetails(Long userId) {
        EntityManager em = emf.createEntityManager();

        try {
            User user = em.find(User.class, userId);

            if (user != null) {
                return user.getUserDetails();
            }

            return null;
        } finally {
            em.close();
        }
    }

    public void deleteUserDetails(Long userId){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);

            if (user != null && user.getUserDetails()!=null) {
                user.getUserDetails().setUser(null);
                user.setUserDetails(null);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
