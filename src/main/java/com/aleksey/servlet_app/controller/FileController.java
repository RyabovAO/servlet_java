package com.aleksey.servlet_app.controller;

import com.aleksey.servlet_app.model.File;
import com.aleksey.servlet_app.services.FileServices;

import java.util.List;

public class FileController {

    private FileServices fileServices;

    public FileController() {
        this.fileServices = new FileServices();
    }

    public File createFile(File file) {
        return fileServices.createFile(file);
    }

    public File updateFile(File file) {
        return fileServices.updateFile(file);
    }

    public List<File> getAllFile() {
        return fileServices.getAllFile();
    }

    public File getFileById(Integer id) {
        return fileServices.getFileById(id);
    }

    public void deleteFileById(Integer id) {
        fileServices.deleteFileById(id);
    }
}
