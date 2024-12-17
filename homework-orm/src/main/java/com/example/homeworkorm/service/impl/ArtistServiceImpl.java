package com.example.homeworkorm.service.impl;

import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.exception.EntityNotFoundException;
import com.example.homeworkorm.repository.ArtistRepository;
import com.example.homeworkorm.service.ArtistService;
import com.example.homeworkorm.util.ExceptionMessageUtil;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;

    public ArtistServiceImpl(ArtistRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Artist> getAll() {
        return repository.findAll();
    }

    @Override
    public Artist getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound("Artist", id)
            ));
    }

    @Override
    public void add(Artist artist) {
        repository.save(artist);
    }

    @Override
    public void update(Long id, Artist artist) {
        Optional<Artist> existingArtist = repository.findById(id);
        if (existingArtist.isPresent()) {
            Artist artistToUpdate = existingArtist.get();
            artistToUpdate.setName(artist.getName());
            artistToUpdate.setSpecialty(artist.getSpecialty());
            artistToUpdate.setBio(artist.getBio());
            artistToUpdate.setMasterpieces(artist.getMasterpieces());
            repository.update(artistToUpdate);
            return;
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("Artist", id));
    }

    @Override
    public void delete(Long id) {
        Optional<Artist> existingArtist = repository.findById(id);
        if (existingArtist.isPresent()) {
            repository.delete(id);
            return;
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("Artist", id));
    }

}
