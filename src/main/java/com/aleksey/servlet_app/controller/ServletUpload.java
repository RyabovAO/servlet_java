package com.aleksey.servlet_app.controller;


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

public class ServletUpload extends HttpServlet {

    private final String FILE_PATH = "src/main/file_storage/";
    private File file;

    private final int fileMaxSize = 100 * 1024;
    private final int memMaxSize = 100 * 1024;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();

        String docType = "<!DOCTYPE html>";

        writer.println(docType +
                "<html>" +
                "<head>" +

                "</head>" +
                "<body>");

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(memMaxSize);
        diskFileItemFactory.setRepository(new File(FILE_PATH));

        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setSizeMax(fileMaxSize);

        try {
            List fileItemList = servletFileUpload.parseRequest(req);

            Iterator iter = fileItemList.iterator();
            while (iter.hasNext()) {
                FileItem fileItem = (FileItem) iter.next();
                if (!fileItem.isFormField()) {
                    String fileName = fileItem.getName();
                    file = new File(FILE_PATH + fileName);
                    fileItem.write(file);
                    writer.println(fileName + " is uploaded");
                }
            }
            writer.println("</body>" +
                    "</html>");

        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

