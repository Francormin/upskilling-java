package com.example.homeworkorm.repository.impl;

import com.example.homeworkorm.config.HibernateConfig;
import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.entity.Masterpiece;
import com.example.homeworkorm.repository.MasterpieceRepository;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MasterpieceRepositoryImpl implements MasterpieceRepository {

    @Override
    public List<Masterpiece> findAll() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM Masterpiece", Masterpiece.class).list();
        }
    }

    @Override
    public Optional<Masterpiece> findById(Long id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Masterpiece.class, id));
        }
    }

    @Override
    public void save(Masterpiece masterpiece) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.save(masterpiece);
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
    public void update(Masterpiece masterpiece) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.update(masterpiece);
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
                        "DELETE FROM gallery_masterpiece WHERE masterpiece_id = :masterpieceId")
                    .setParameter("masterpieceId", id)
                    .executeUpdate();

                // Recupera la entidad Masterpiece
                Masterpiece masterpiece = session.get(Masterpiece.class, id);

                // Elimina la referencia de la masterpiece del artista
                Artist artist = masterpiece.getArtist();
                artist.getMasterpieces().remove(masterpiece);

                // Elimina la entidad masterpiece
                session.delete(masterpiece);

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
    public List<Masterpiece> findByArtistId(Long artistId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            String hql = "FROM Masterpiece m WHERE m.artist.id = :artistId";
            return session.createQuery(hql, Masterpiece.class)
                .setParameter("artistId", artistId)
                .list();
        }
    }

}
