package com.aleksey.servlet_app.servlet;


import com.aleksey.servlet_app.controller.UserController;
import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.model.EventType;
import com.aleksey.servlet_app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class Upload extends HttpServlet {

    private final String FILE_PATH = "src/main/file_storage/";
    private File file;
    private UserController userController = new UserController();
    private final int fileMaxSize = 100 * 1024;
    private final int memMaxSize = 100 * 1024;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        PrintWriter writer = resp.getWriter();
        String id = req.getParameter("id");

        User currentUser = userController.getAllUser().stream().filter(user -> user.getId().equals(Integer.parseInt(id))).findFirst().orElse(null);

        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "ID not found");
        } else {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setSizeThreshold(memMaxSize);
            diskFileItemFactory.setRepository(new File(FILE_PATH));

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
                        file = new File(FILE_PATH + fileName);
                        fileItem.write(file);
                        String json = new ObjectMapper().writeValueAsString(fileName);
                        writer.write(json);
                    }
                }

                com.aleksey.servlet_app.model.File newFile = new com.aleksey.servlet_app.model.File();
                newFile.setFilePath(FILE_PATH);
                newFile.setName(fileName);

                Event event = new Event();
                event.setUser(currentUser);
                event.setEventType(EventType.DOWNLOAD);
                event.setFile(newFile);

                currentUser.addEvent(event);

                userController.updateUser(currentUser);

            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}

