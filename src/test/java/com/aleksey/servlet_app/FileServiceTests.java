package com.aleksey.servlet_app;

import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.repository.hibernate_db.FileRepositoryImpl;
import com.aleksey.servlet_app.services.FileService;
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
public class FileServiceTests {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepositoryImpl fileRepository;

    @Test
    void createFileTest() {
        FileEntity file = new FileEntity(1, "name", "path");

        Mockito.when(fileRepository.create(file)).thenReturn(file);

        FileEntity fileTest = fileService.createFile(file);

        Assertions.assertEquals(file, fileTest);
    }

    @Test
    void getAllFileTest() {
        List<FileEntity> list = new ArrayList<>(Arrays.asList(
                new FileEntity(1, "name", "path"),
                new FileEntity(2, "name2", "path2"),
                new FileEntity(3, "name3", "path3")
        ));

        Mockito.when(fileRepository.readAll()).thenReturn(list);

        List<FileEntity> listTest = fileService.getAllFile();

        Assertions.assertEquals(list, listTest);
    }

    @Test
    void getAllFileListIsEmptyTest() {
        List<FileEntity> list = new ArrayList<>();

        Mockito.when(fileRepository.readAll()).thenReturn(list);

        List<FileEntity> listTest = fileService.getAllFile();

        Assertions.assertTrue(listTest.isEmpty());
    }

    @Test
    void getFileByIdTest() {
        int id = 1;
        FileEntity file = new FileEntity(1, "name", "path");
        FileEntity file2 = new FileEntity(2, "name2", "path2");
        List<FileEntity> list = new ArrayList<>(Arrays.asList(file, file2));

        Mockito.when(fileRepository.readAll()).thenReturn(list);
        Mockito.when(fileRepository.readById(id)).thenReturn(file);

        FileEntity fileTest = fileService.getFileById(id);

        Assertions.assertEquals(file, fileTest);
    }

    @Test
    void getFileByIdIsNotFoundTest() {
        int id = 3;
        FileEntity file = new FileEntity(1, "name", "path");
        FileEntity file2 = new FileEntity(2, "name2", "path2");
        FileEntity file3 = new FileEntity(0, "file with id = " + id + " not found", "");;
        List<FileEntity> list = new ArrayList<>(Arrays.asList(file, file2));

        Mockito.when(fileRepository.readAll()).thenReturn(list);
        Mockito.when(fileRepository.readById(id)).thenReturn(file);

        FileEntity fileTest = fileService.getFileById(id);

        Assertions.assertEquals(file3, fileTest);
    }
}
