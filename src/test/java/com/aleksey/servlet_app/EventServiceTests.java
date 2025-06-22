package com.aleksey.servlet_app;

import com.aleksey.servlet_app.entity.EventEntity;
import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.hibernate_db.EventRepositoryImpl;
import com.aleksey.servlet_app.services.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aleksey.servlet_app.entity.EventType.DOWNLOAD;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EventServiceTests {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepositoryImpl eventRepository;

    @Test
    void getAllEventTest() {
        UserEntity user = new UserEntity(1, "userName", new ArrayList<>());
        FileEntity file = new FileEntity(1, "fileName", "filePath");

        List<EventEntity> list = new ArrayList<>(Arrays.asList(
                new EventEntity(1, user, file, DOWNLOAD)
        ));

        Mockito.when(eventRepository.readAll()).thenReturn(list);

        List<EventEntity> listTest = eventService.getAllEvent();

        Assertions.assertEquals(list, listTest);
    }

    @Test
    void getAllEventsIfListIsEmptyTest() {
        List<EventEntity> list = new ArrayList<>();

        Mockito.when(eventRepository.readAll()).thenReturn(list);

        List<EventEntity> listTest = eventService.getAllEvent();

        Assertions.assertTrue(listTest.isEmpty());
    }

    @Test
    void getEventByIdTest() {
        int id = 1;
        UserEntity user = new UserEntity(1, "userName", new ArrayList<>());
        FileEntity file = new FileEntity(1, "fileName", "filePath");

        EventEntity event = new EventEntity(1, user, file, DOWNLOAD);

        Mockito.when(eventRepository.readById(id)).thenReturn(event);

        EventEntity eventTest = eventService.getEventById(id);

        Assertions.assertEquals(event, eventTest);

    }

    @Test
    void getEventByIdIfIdNotFoundTest() {
        int id = 13;

        Mockito.when(eventRepository.readById(id)).thenReturn(null);

        EventEntity eventTest = eventService.getEventById(id);

        Assertions.assertNull(eventTest);
    }
}


