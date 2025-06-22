package com.aleksey.servlet_app;

import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.hibernate_db.UserRepositoryImpl;
import com.aleksey.servlet_app.services.UserService;
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

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Test
    void createUserTest() {
        UserEntity user = new UserEntity(1, "name", new ArrayList<>());

        Mockito.when(userRepository.create(user)).thenReturn(user);

        UserEntity userTest = userService.createUser(user);

        Assertions.assertEquals(user, userTest);
    }

    @Test
    void createUserWithNameIsNullTest() {
        UserEntity user = new UserEntity(1, "", new ArrayList<>());

        Mockito.when(userRepository.create(user)).thenReturn(null);

        UserEntity userTest = userService.createUser(user);

        Assertions.assertNull(userTest);
    }

    @Test
    void updateUserTest() {
        UserEntity user = new UserEntity(1, "name", new ArrayList<>());

        Mockito.when(userRepository.update(user)).thenReturn(user);

        UserEntity userTest = userService.updateUser(user);

        Assertions.assertEquals(user, userTest);
    }

    @Test
    void updateUserIfIdNotFoundTest() {
        UserEntity user = new UserEntity(1, "", new ArrayList<>());

        Mockito.when(userRepository.update(user)).thenReturn(null);

        UserEntity userTest = userService.updateUser(user);

        Assertions.assertNull(userTest);
    }

    @Test
    void getAllUserTest() {
        List<UserEntity> list = new ArrayList<>(Arrays.asList(
                new UserEntity(1, "name", new ArrayList<>()),
                new UserEntity(2, "name2", new ArrayList<>()),
                new UserEntity(3, "name3", new ArrayList<>())
        ));

        Mockito.when(userRepository.readAll()).thenReturn(list);

        List<UserEntity> listTest = userService.getAllUser();

        Assertions.assertEquals(list, listTest);
    }

    @Test
    void getAllUserIfListIsEmptyTest() {
        List<UserEntity> list = new ArrayList<>();

        Mockito.when(userRepository.readAll()).thenReturn(list);

        List<UserEntity> listTest = userService.getAllUser();

        Assertions.assertTrue(listTest.isEmpty());
    }

    @Test
    void getUserByIdTest() {
        int id = 1;

        UserEntity user = new UserEntity(1, "name", new ArrayList<>());

        Mockito.when(userRepository.readById(id)).thenReturn(user);

        UserEntity userTest = userService.getUserById(id);

        Assertions.assertEquals(user, userTest);
    }

    @Test
    void getUserByIdIfIdNotFoundTest() {
        int id = 15;

        Mockito.when(userRepository.readById(id)).thenReturn(null);

        UserEntity userTest = userService.getUserById(id);

        Assertions.assertNull(userTest);
    }

    @Test
    void deleteUserByIdTest() {
        int id = 1;

        Mockito.doNothing().when(userRepository).delete(id);

        userService.deleteUserById(id);

        Mockito.verify(userRepository).delete(id);
    }
}
