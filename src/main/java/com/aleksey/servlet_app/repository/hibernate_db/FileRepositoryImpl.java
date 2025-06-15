package com.aleksey.servlet_app.repository.hibernate_db;

import com.aleksey.servlet_app.config.HibernateConfig;
import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.repository.FileRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {

    @Override
    public FileEntity create(FileEntity file) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.persist(file);
            session.getTransaction().commit();
        }
        return file;
    }

    @Override
    public FileEntity readById(Integer fileId) {
        FileEntity file;
        try (Session session = HibernateConfig.getSession()) {
            Query query = session.createQuery("FROM FileEntity f WHERE f.id = :id", FileEntity.class)
                    .setParameter("id", fileId);
            file = (FileEntity) query.getSingleResult();
        }
        return file;
    }

    @Override
    public List<FileEntity> readAll() {
        List<FileEntity> fileList;
        try (Session session = HibernateConfig.getSession()) {
            Query query = session.createQuery("FROM FileEntity", FileEntity.class);
            fileList = query.getResultList();
        }
        return fileList;
    }

    @Override
    public FileEntity update(FileEntity file) {
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.merge(file);
            session.getTransaction().commit();
        }
        return file;
    }

    @Override
    public void delete(Integer fileId) {
        FileEntity file = readById(fileId);
        try (Session session = HibernateConfig.getSession()) {
            session.beginTransaction();
            session.remove(file);
            session.getTransaction().commit();
        }
    }
}
