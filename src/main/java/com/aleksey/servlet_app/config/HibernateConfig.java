package com.aleksey.servlet_app.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if(sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(com.aleksey.servlet_app.entity.EventEntity.class)
                    .addAnnotatedClass(com.aleksey.servlet_app.entity.FileEntity.class)
                    .addAnnotatedClass(com.aleksey.servlet_app.entity.UserEntity.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return getSessionFactory().openSession();
    }
}
