package com.aleksey.servlet_app.repository.hibernate_db;

import com.aleksey.servlet_app.config.HibernateConfig;
import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.UserRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserEntity create(UserEntity user) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public UserEntity readById(Integer userId) {
        UserEntity user;
        try (Session session = HibernateConfig.getSession()) {
            Query query = session.createQuery("FROM UserEntity u LEFT JOIN FETCH u.events WHERE u.id = :id", UserEntity.class)
                    .setParameter("id", userId);
            user = (UserEntity) query.getResultList().stream().findFirst().orElse(null);
        }
        return user;
    }

    @Override
    public List<UserEntity> readAll() {
        List<UserEntity> userList;
        try (Session session = HibernateConfig.getSession()) {
            Query query = session.createQuery("FROM UserEntity u LEFT JOIN FETCH u.events", UserEntity.class);
            userList = query.getResultList();
        }
        return userList;
    }

    @Override
    public UserEntity update(UserEntity user) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public void delete(Integer userId) {
        UserEntity user = readById(userId);
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        }
    }
}
