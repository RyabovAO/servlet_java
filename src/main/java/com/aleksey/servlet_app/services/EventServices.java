package com.aleksey.servlet_app.services;

import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.repository.EventRepository;
import com.aleksey.servlet_app.repository.hibernate_db.EventRepositoryImpl;

import java.util.List;

public class EventServices {

    private EventRepository eventRepository;

    public EventServices() {
        this.eventRepository = new EventRepositoryImpl();
    }

    public EventServices(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.create(event);
    }

    public Event updateEvent(Event event) {
        return eventRepository.update(event);
    }

    public List<Event> getAllEvent() {
        return eventRepository.readAll();
    }

    public Event getEventById(Integer id) {
        return eventRepository.readById(id);
    }

    public void deleteEventById(Integer id) {
        eventRepository.delete(id);
    }
}
