package com.example.homeworkorm.repository;

import com.example.homeworkorm.entity.Masterpiece;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterpieceRepository extends JpaRepository<Masterpiece, Long> {

    /**
     * Encuentra todas las obras de arte asociadas con un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de obras de arte.
     */
    // SELECT m FROM Masterpiece m JOIN FETCH m.artist WHERE m.artist.id = :artistId
    // @Query("SELECT m FROM Masterpiece m WHERE m.artist.id = :artistId")
    List<Masterpiece> findByArtistId(@Param("artistId") Long artistId);

}
