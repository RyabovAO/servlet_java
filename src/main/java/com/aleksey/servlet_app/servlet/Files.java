package com.aleksey.servlet_app.servlet;

import com.aleksey.servlet_app.controller.FileController;
import com.aleksey.servlet_app.model.File;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Files extends HttpServlet {

    private FileController fileController = new FileController();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> list = fileController.getAllFile();
        PrintWriter messageWriter = resp.getWriter();

        for (int i = 0; i < list.size(); i++) {
            String json = objectMapper.writeValueAsString(list.get(i));
            messageWriter.write(json + "\n");
        }
        messageWriter.close();
    }
}
