package com.aleksey.servlet_app.repository.hibernate_db;

import com.aleksey.servlet_app.config.HibernateConfig;
import com.aleksey.servlet_app.entity.EventEntity;
import com.aleksey.servlet_app.repository.EventRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public EventEntity create(EventEntity event) {
        try(Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.persist(event);
            session.getTransaction().commit();
        }
        return event;
    }

    @Override
    public EventEntity readById(Integer eventId) {
        EventEntity event;
        try(Session session = HibernateConfig.getSession()) {
            Query query = session.createQuery("FROM EventEntity e JOIN FETCH e.user JOIN FETCH e.file WHERE e.id = :id", EventEntity.class)
                    .setParameter("id", eventId);
            event = (EventEntity) query.getSingleResult();
        }
        return event;
    }

    @Override
    public List<EventEntity> readAll() {
        List<EventEntity> eventList;
        try(Session session = HibernateConfig.getSession()) {
            Query query = session.createQuery("FROM EventEntity e JOIN FETCH e.user JOIN FETCH e.file", EventEntity.class);
            eventList = query.getResultList();
        }
        return eventList;
    }

    @Override
    public EventEntity update(EventEntity event) {
        try(Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.merge(event);
            session.getTransaction().commit();
        }
        return event;
    }

    @Override
    public void delete(Integer eventId) {
        EventEntity event = readById(eventId);
        try(Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.remove(event);
            session.getTransaction().commit();
        }
    }
}
