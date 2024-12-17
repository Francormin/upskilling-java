package com.example.homeworkorm.repository.impl;

import com.example.homeworkorm.config.HibernateConfig;
import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.repository.ArtistRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArtistRepositoryImpl implements ArtistRepository {

    @Override
    public List<Artist> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM Artist", Artist.class).list();
        }
    }

    @Override
    public Optional<Artist> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Artist.class, id));
        }
    }

    @Override
    public void save(Artist artist) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.save(artist);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public void update(Artist artist) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.update(artist);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();

                // Elimina las relaciones en la tabla intermedia gallery_masterpiece usando SQL nativo
                session.createNativeQuery("DELETE FROM gallery_masterpiece WHERE masterpiece_id IN " +
                        "(SELECT m.id FROM masterpieces m WHERE m.artist_id = :artistId)")
                    .setParameter("artistId", id)
                    .executeUpdate();

                // Elimina la entidad artist
                // JPA manejará la eliminación de las entidades masterpieces si se configuró con cascade
                Artist artist = session.get(Artist.class, id);
                session.delete(artist);

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

}
