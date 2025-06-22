package com.aleksey.servlet_app;

import com.aleksey.servlet_app.entity.EventEntity;
import com.aleksey.servlet_app.entity.EventType;
import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.hibernate_db.FileRepositoryImpl;
import com.aleksey.servlet_app.services.FileService;
import com.aleksey.servlet_app.services.UserService;
import com.aleksey.servlet_app.utils.HttpUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileServiceTests {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepositoryImpl fileRepository;

    @Mock
    private UserService userService;

    @Mock
    private ServletFileUpload servletFileUpload;

    @Test
    void createFileTest() {
        FileEntity file = new FileEntity(1, "name", "path");

        Mockito.when(fileRepository.create(file)).thenReturn(file);

        FileEntity fileTest = fileService.createFile(file);

        assertEquals(file, fileTest);
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

        assertEquals(list, listTest);
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

        assertEquals(file, fileTest);
    }

    @Test
    void getFileByIdIsNotFoundTest() {
        int id = 3;

        Mockito.when(fileRepository.readById(id)).thenReturn(null);

        FileEntity fileTest = fileService.getFileById(id);

        Assertions.assertNull(fileTest);
    }

    @Test
    void uploadTest() throws Exception {
        String id = "1";
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

        UserEntity user = new UserEntity(1, "userName", new ArrayList<>());
        UserEntity userUpdate = new UserEntity(1, "userName", new ArrayList<>());
        Mockito.when(request.getContentType()).thenReturn("multipart/form-data; boundary=/");
        Mockito.when(request.getPathInfo()).thenReturn(id);
        Mockito.when(request.getMethod()).thenReturn("POST");
        Mockito.when(userService.getUserById(Integer.parseInt(id))).thenReturn(user);
        Mockito.when(userService.updateUser(user)).thenReturn(userUpdate);

        FileItem fileItem = Mockito.mock(FileItem.class);
        Mockito.when(fileItem.isFormField()).thenReturn(false);
        Mockito.when(fileItem.getName()).thenReturn("filename.txt");
        Mockito.doNothing().when(fileItem).write(Mockito.any(File.class));
        List<FileItem> fileItems = Collections.singletonList(fileItem);

//        ServletFileUpload servletFileUpload = Mockito.mock(ServletFileUpload.class);

        PrintWriter writer = Mockito.mock(PrintWriter.class);
        Mockito.when(response.getWriter()).thenReturn(writer);

        Mockito.doNothing().when(servletFileUpload).setFileItemFactory(new DiskFileItemFactory());
        Mockito.doNothing().when(servletFileUpload).setSizeMax(1);
        Mockito.when(servletFileUpload.parseRequest(request)).thenReturn(fileItems);
        fileService.upload(request, response);

        Mockito.verify(servletFileUpload).parseRequest(request);
        Mockito.verify(userService).updateUser(userUpdate);
        Mockito.verify(writer).write(Mockito.anyString());
    }

    @Test
    void uploadFailTest() {

    }
}
