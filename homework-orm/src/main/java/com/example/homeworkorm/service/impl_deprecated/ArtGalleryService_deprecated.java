package com.example.homeworkorm.service.impl_deprecated;

import com.example.homeworkorm.exception.EntityNotFoundException;
import com.example.homeworkorm.repository.ArtGalleryRepository;
import com.example.homeworkorm.repository.ArtistRepository;

import org.springframework.stereotype.Service;

@Deprecated
@Service
public class ArtGalleryService_deprecated {

    private final ArtistRepository artistRepository;

    public ArtGalleryService_deprecated(ArtGalleryRepository repository, ArtistRepository artistRepository) {
//        super(repository, "Art Gallery");
        this.artistRepository = artistRepository;
    }

    /**
     * Obtiene todas las galerías de arte en las que exhibe un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de galerías de arte asociadas al artista.
     * @throws EntityNotFoundException si no existe un artista con el ID proporcionado.
     */
//    public List<ArtGallery> getArtGalleriesByArtist(Long artistId) {
//        if (!artistRepository.existsById(artistId)) {
//            throw new EntityNotFoundException(
//                ExceptionMessageUtil.entityNotFound("Artist", artistId)
//            );
//        }
//        return repository.findByArtistId(artistId);
//    }

}
