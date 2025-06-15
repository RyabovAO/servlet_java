package com.aleksey.servlet_app.rest;

import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.services.FileService;
import com.aleksey.servlet_app.utils.HttpUtils;
import com.aleksey.servlet_app.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FileRestControllerV1 extends HttpServlet {

    private FileService fileService = new FileService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter messageWriter = resp.getWriter();
        String id = HttpUtils.getIdFromRequest(req);
        String json = "";

        if (id == null || id.isEmpty()) {
            List<FileEntity> list = fileService.getAllFile();
            for (int i = 0; i < list.size(); i++) {
                json = JsonUtils.writeJsonAsString(list.get(i));
                messageWriter.write(json + "\n");
            }
        } else {
            FileEntity file = fileService.getFileById(Integer.parseInt(id));
            json = JsonUtils.writeJsonAsString(file);
            messageWriter.write(json);
        }
        messageWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        fileService.upload(req, resp);
    }
}
