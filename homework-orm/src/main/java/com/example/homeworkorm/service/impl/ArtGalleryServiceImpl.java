package com.example.homeworkorm.service.impl;

import com.example.homeworkorm.entity.ArtGallery;
import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.exception.EntityNotFoundException;
import com.example.homeworkorm.repository.ArtGalleryRepository;
import com.example.homeworkorm.repository.ArtistRepository;
import com.example.homeworkorm.service.ArtGalleryService;
import com.example.homeworkorm.util.ExceptionMessageUtil;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtGalleryServiceImpl implements ArtGalleryService {

    private final ArtGalleryRepository repository;
    private final ArtistRepository artistRepository;

    public ArtGalleryServiceImpl(ArtGalleryRepository repository, ArtistRepository artistRepository) {
        this.repository = repository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<ArtGallery> getAll() {
        return repository.findAll();
    }

    @Override
    public ArtGallery getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound("ArtGallery", id)
            ));
    }

    @Override
    public void add(ArtGallery artGallery) {
        repository.save(artGallery);
    }

    @Override
    public void update(Long id, ArtGallery artGallery) {
        Optional<ArtGallery> existingArtGallery = repository.findById(id);
        if (existingArtGallery.isPresent()) {
            ArtGallery artGalleryToUpdate = existingArtGallery.get();
            artGalleryToUpdate.setName(artGallery.getName());
            artGalleryToUpdate.setDate(artGallery.getDate());
            artGalleryToUpdate.setMasterpieces(artGallery.getMasterpieces());
            repository.update(artGalleryToUpdate);
            return;
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("ArtGallery", id));
    }

    @Override
    public void delete(Long id) {
        Optional<ArtGallery> existingArtGallery = repository.findById(id);
        if (existingArtGallery.isPresent()) {
            repository.delete(id);
            return;
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("ArtGallery", id));
    }

    @Override
    public List<ArtGallery> getByArtistId(Long artistId) {
        Optional<Artist> existingArtist = artistRepository.findById(artistId);
        if (existingArtist.isPresent()) {
            return repository.findByArtistId(artistId);
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("Artist", artistId));
    }

}
