package com.aleksey.servlet_app.repository.hibernate_db;

import com.aleksey.servlet_app.Utils.HibernateUtil;
import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.repository.EventRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    @Override
    public Event create(Event event) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(event);
            session.getTransaction().commit();
        }
        return event;
    }

    @Override
    public Event readById(Integer eventId) {
        Event event;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Event e WHERE e.id = :id", Event.class)
                    .setParameter("id", eventId);
            event = (Event) query.getSingleResult();
        }
        return event;
    }

    @Override
    public List<Event> readAll() {
        List<Event> eventList;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM Event", Event.class);
            eventList = query.getResultList();
        }
        return eventList;
    }

    @Override
    public Event update(Event event) {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction();
            session.merge(event);
            session.getTransaction().commit();
        }
        return event;
    }

    @Override
    public void delete(Integer eventId) {
        Event event = readById(eventId);
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(event);
            session.getTransaction().commit();
        }
    }
}
