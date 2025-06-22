package com.aleksey.servlet_app.services;

import com.aleksey.servlet_app.entity.EventEntity;
import com.aleksey.servlet_app.entity.EventType;
import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.FileRepository;
import com.aleksey.servlet_app.repository.hibernate_db.FileRepositoryImpl;
import com.aleksey.servlet_app.utils.HttpUtils;
import com.aleksey.servlet_app.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileService {

    private final String FILE_PATH = "src/main/file_storage/";
    private java.io.File file;

    private final int fileMaxSize = 100 * 1024;
    private final int memMaxSize = 100 * 1024;

    private final FileRepository fileRepository;
    private UserService userService;
    private DiskFileItemFactory diskFileItemFactory;
    private ServletFileUpload servletFileUpload;

    public FileService() {
        this.fileRepository = new FileRepositoryImpl();
        this.diskFileItemFactory = new DiskFileItemFactory(memMaxSize, new java.io.File(FILE_PATH));
        this.servletFileUpload = new ServletFileUpload();
    }

    public FileService(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    public FileEntity createFile(FileEntity file) {
        return fileRepository.create(file);
    }

    public FileEntity updateFile(FileEntity file) {
        return fileRepository.update(file);
    }

    public List<FileEntity> getAllFile() {
        List<FileEntity> list = fileRepository.readAll();
        return !list.isEmpty() ? list : new ArrayList<>();
    }

    public FileEntity getFileById(Integer id) {
        return fileRepository.readById(id);
    }

    public void deleteFileById(Integer id) {
        fileRepository.delete(id);
    }

    public void upload(HttpServletRequest req, HttpServletResponse resp) {

        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String id = HttpUtils.getIdFromRequest(req);

        UserEntity currentUser = userService.getUserById(Integer.parseInt(id));

        if (currentUser == null) {
            try {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "ID not found");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        servletFileUpload.setFileItemFactory(diskFileItemFactory);
        servletFileUpload.setSizeMax(fileMaxSize);

        String fileName = null;

        try {
            List fileItemList = servletFileUpload.parseRequest(req);

            Iterator iter = fileItemList.iterator();
            while (iter.hasNext()) {
                FileItem fileItem = (FileItem) iter.next();
                if (!fileItem.isFormField()) {
                    fileName = fileItem.getName();
                    file = new java.io.File(FILE_PATH + fileName);
                    fileItem.write(file);
                    String json = JsonUtils.writeJsonAsString(fileName);
                    writer.write(json);
                }
            }

            FileEntity newFile = new FileEntity();
            newFile.setFilePath(FILE_PATH);
            newFile.setName(fileName);

            EventEntity event = new EventEntity();
            event.setUser(currentUser);
            event.setEventType(EventType.DOWNLOAD);
            event.setFile(newFile);

            currentUser.addEvent(event);

            userService.updateUser(currentUser);

        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
