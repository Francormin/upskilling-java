package com.example.homeworkorm.repository;

import com.example.homeworkorm.entity.Masterpiece;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MasterpieceRepository extends IRepository<Masterpiece> {

    /**
     * Encuentra todas las obras de arte asociadas con un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de obras de arte.
     */
    // SELECT m FROM Masterpiece m JOIN FETCH m.artist WHERE m.artist.id = :artistId
    // @Query("SELECT m FROM Masterpiece m WHERE m.artist.id = :artistId")
    // List<Masterpiece> findByArtistId(@Param("artistId") Long artistId);

    List<Masterpiece> findByArtistId(Long artistId);

}
