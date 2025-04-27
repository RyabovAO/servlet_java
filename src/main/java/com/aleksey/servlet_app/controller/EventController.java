package com.aleksey.servlet_app.controller;

import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.services.EventServices;

import java.util.List;

public class EventController {

    private EventServices eventServices;

    public EventController() {
        this.eventServices = new EventServices();
    }

    public Event createEvent(Event event) {
        return eventServices.createEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventServices.updateEvent(event);
    }

    public List<Event> getAllEvent() {
        return eventServices.getAllEvent();
    }

    public Event getEventById(Integer id) {
        return eventServices.getEventById(id);
    }

    public void deleteEventById(Integer id) {
        eventServices.deleteEventById(id);
    }
}
