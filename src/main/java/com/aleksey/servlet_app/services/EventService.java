package com.aleksey.servlet_app.services;

import com.aleksey.servlet_app.entity.EventEntity;
import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.EventRepository;
import com.aleksey.servlet_app.repository.hibernate_db.EventRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class EventService {

    private EventRepository eventRepository;

    public EventService() {
        this.eventRepository = new EventRepositoryImpl();
    }

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventEntity createEvent(EventEntity event) {
        return eventRepository.create(event);
    }

    public EventEntity updateEvent(EventEntity event) {
        return eventRepository.update(event);
    }

    public List<EventEntity> getAllEvent() {
        List<EventEntity> list = eventRepository.readAll();
        return !list.isEmpty() ? list : new ArrayList<>();
    }

    public EventEntity getEventById(Integer id) {
        return eventRepository.readById(id);
    }

    public void deleteEventById(Integer id) {
        eventRepository.delete(id);
    }
}
