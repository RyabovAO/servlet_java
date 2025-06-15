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
    private UserService userService = new UserService();
    private final int fileMaxSize = 100 * 1024;
    private final int memMaxSize = 100 * 1024;

    private FileRepository fileRepository;

    public FileService() {
        this.fileRepository = new FileRepositoryImpl();
    }

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
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
        if(getAllFile().size() >= id) {
            return fileRepository.readById(id);
        } else {
            return new FileEntity(0, "file with id = " + id + " not found", "");
        }
    }

    public void deleteFileById(Integer id) {
        fileRepository.delete(id);
    }

    public void upload(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        String id = HttpUtils.getIdFromRequest(req);

        UserEntity currentUser = userService.getAllUser().stream()
                .filter(user -> user.getId().equals(Integer.parseInt(id)))
                .findFirst()
                .orElse(null);

        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "ID not found");
        } else {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(memMaxSize);
            diskFileItemFactory.setRepository(new java.io.File(FILE_PATH));

            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
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
}
