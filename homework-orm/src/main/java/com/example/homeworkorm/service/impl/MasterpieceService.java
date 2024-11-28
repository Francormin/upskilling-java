package com.example.homeworkorm.service.impl;

import com.example.homeworkorm.entity.Masterpiece;
import com.example.homeworkorm.exception.EntityNotFoundException;
import com.example.homeworkorm.repository.ArtistRepository;
import com.example.homeworkorm.repository.MasterpieceRepository;
import com.example.homeworkorm.util.ExceptionMessageUtil;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterpieceService extends AbstractService<Masterpiece, MasterpieceRepository> {

    private final ArtistRepository artistRepository;

    public MasterpieceService(MasterpieceRepository repository, ArtistRepository artistRepository) {
        super(repository, "Masterpiece");
        this.artistRepository = artistRepository;
    }

    /**
     * Obtiene todas las obras de arte de un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de obras de arte asociadas al artista.
     * @throws EntityNotFoundException si no existe un artista con el ID proporcionado.
     */
    public List<Masterpiece> getMasterpiecesByArtist(Long artistId) {
        if (!artistRepository.existsById(artistId)) {
            throw new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound("Artist", artistId)
            );
        }
        return repository.findByArtistId(artistId);
    }

    /**
     * Obtiene todas las galerías de arte en las que exhibe un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de galerías de arte asociadas al artista.
     * @throws EntityNotFoundException si no existe un artista con el ID proporcionado.
     */
    /*
    public List<ArtGallery> getArtGalleriesByArtist(Long artistId) {
        // Consultar todas las obras del artista
        List<Masterpiece> masterpieces = getMasterpiecesByArtist(artistId);

        // Recopilar las galerías relacionadas con esas obras
        return masterpieces.stream()
            .flatMap(masterpiece -> masterpiece.getArtGalleries().stream())
            .distinct()
            .toList();
    }
    */

}
