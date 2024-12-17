package com.example.homeworkorm.repository;

import com.example.homeworkorm.entity.ArtGallery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtGalleryRepository extends IRepository<ArtGallery> {

    /**
     * Encuentra todas las galerías de arte asociadas con un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de galerías de arte.
     */
    // SELECT DISTINCT g FROM ArtGallery g JOIN FETCH g.masterpieces m WHERE m.artist.id = :artistId
    // @Query("SELECT DISTINCT g FROM ArtGallery g JOIN g.masterpieces m WHERE m.artist.id = :artistId")
    // List<ArtGallery> findByArtistId(@Param("artistId") Long artistId);

    List<ArtGallery> findByArtistId(Long artistId);

}
