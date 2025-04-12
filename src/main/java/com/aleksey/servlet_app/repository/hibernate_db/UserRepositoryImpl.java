package com.aleksey.servlet_app.repository.hibernate_db;

import com.aleksey.servlet_app.Utils.HibernateUtil;
import com.aleksey.servlet_app.model.User;
import com.aleksey.servlet_app.repository.UserRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public User create(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User readById(Integer userId) {
        User user;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User u LEFT JOIN FETCH u.events WHERE u.id = :id", User.class)
                    .setParameter("id", userId);
            user = (User) query.getSingleResult();
        }
        return user;
    }

    @Override
    public List<User> readAll() {
        List<User> userList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User u LEFT JOIN FETCH u.events", User.class);
            userList = query.getResultList();
        }
        return userList;
    }

    @Override
    public User update(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public void delete(Integer userId) {
        User user = readById(userId);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        }
    }
}
