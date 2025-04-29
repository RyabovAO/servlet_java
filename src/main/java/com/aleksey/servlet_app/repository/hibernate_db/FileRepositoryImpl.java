package com.aleksey.servlet_app.repository.hibernate_db;

import com.aleksey.servlet_app.Utils.HibernateUtil;
import com.aleksey.servlet_app.model.File;
import com.aleksey.servlet_app.repository.FileRepository;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {

    @Override
    public File create(File file) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(file);
            session.getTransaction().commit();
        }
        return file;
    }

    @Override
    public File readById(Integer fileId) {
        File file;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM File f WHERE f.id = :id", File.class)
                    .setParameter("id", fileId);
            file = (File) query.getSingleResult();
        }
        return file;
    }

    @Override
    public List<File> readAll() {
        List<File> fileList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM File", File.class);
            fileList = query.getResultList();
        }
        return fileList;
    }

    @Override
    public File update(File file) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(file);
            session.getTransaction().commit();
        }
        return file;
    }

    @Override
    public void delete(Integer fileId) {
        File file = readById(fileId);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(file);
            session.getTransaction().commit();
        }
    }
}
