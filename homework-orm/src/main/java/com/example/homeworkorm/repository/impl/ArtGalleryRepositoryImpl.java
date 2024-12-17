package com.example.homeworkorm.repository.impl;

import com.example.homeworkorm.config.HibernateConfig;
import com.example.homeworkorm.entity.ArtGallery;
import com.example.homeworkorm.repository.ArtGalleryRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArtGalleryRepositoryImpl implements ArtGalleryRepository {

    @Override
    public List<ArtGallery> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM ArtGallery", ArtGallery.class).list();
        }
    }

    @Override
    public Optional<ArtGallery> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(ArtGallery.class, id));
        }
    }

    @Override
    public void save(ArtGallery artGallery) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.save(artGallery);
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
    public void update(ArtGallery artGallery) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.update(artGallery);
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
                session.createNativeQuery(
                        "DELETE FROM gallery_masterpiece WHERE art_gallery_id = :artGalleryId")
                    .setParameter("artGalleryId", id)
                    .executeUpdate();

                // Elimina la entidad art gallery
                ArtGallery artGallery = session.get(ArtGallery.class, id);
                session.delete(artGallery);

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
    public List<ArtGallery> findByArtistId(Long artistId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            String hql = "SELECT DISTINCT ag FROM ArtGallery ag "
                + "JOIN ag.masterpieces m "
                + "WHERE m.artist.id = :artistId";
            return session.createQuery(hql, ArtGallery.class)
                .setParameter("artistId", artistId)
                .list();
        }
    }

}
