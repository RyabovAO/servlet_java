package com.aleksey.servlet_app.services;

import com.aleksey.servlet_app.model.File;
import com.aleksey.servlet_app.repository.FileRepository;
import com.aleksey.servlet_app.repository.hibernate_db.FileRepositoryImpl;

import java.util.List;

public class FileServices {

    private FileRepository fileRepository;

    public FileServices() {
        this.fileRepository = new FileRepositoryImpl();
    }

    public FileServices(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File createFile(File file) {
        return fileRepository.create(file);
    }

    public File updateFile(File file) {
        return fileRepository.update(file);
    }

    public List<File> getAllFile() {
        return fileRepository.readAll();
    }

    public File getFileById(Integer id) {
        return fileRepository.readById(id);
    }

    public void deleteFileById(Integer id) {
        fileRepository.delete(id);
    }
}
