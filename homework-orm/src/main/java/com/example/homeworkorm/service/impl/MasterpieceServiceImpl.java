package com.example.homeworkorm.service.impl;

import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.entity.Masterpiece;
import com.example.homeworkorm.exception.EntityNotFoundException;
import com.example.homeworkorm.repository.ArtistRepository;
import com.example.homeworkorm.repository.MasterpieceRepository;
import com.example.homeworkorm.service.MasterpieceService;
import com.example.homeworkorm.util.ExceptionMessageUtil;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MasterpieceServiceImpl implements MasterpieceService {

    private final MasterpieceRepository repository;
    private final ArtistRepository artistRepository;

    public MasterpieceServiceImpl(
        MasterpieceRepository repository,
        ArtistRepository artistRepository) {

        this.repository = repository;
        this.artistRepository = artistRepository;

    }

    @Override
    public List<Masterpiece> getAll() {
        return repository.findAll();
    }

    @Override
    public Masterpiece getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                ExceptionMessageUtil.entityNotFound("Masterpiece", id)
            ));
    }

    @Override
    public void add(Masterpiece masterpiece) {
        repository.save(masterpiece);
    }

    @Override
    public void update(Long id, Masterpiece masterpiece) {
        Optional<Masterpiece> existingMasterpiece = repository.findById(id);
        if (existingMasterpiece.isPresent()) {
            Masterpiece masterpieceToUpdate = existingMasterpiece.get();
            masterpieceToUpdate.setTitle(masterpiece.getTitle());
            masterpieceToUpdate.setGenre(masterpiece.getGenre());
            masterpieceToUpdate.setCreationYear(masterpiece.getCreationYear());
            masterpieceToUpdate.setArtist(masterpiece.getArtist());
            masterpieceToUpdate.setArtGalleries(masterpiece.getArtGalleries());
            repository.update(masterpieceToUpdate);
            return;
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("Masterpiece", id));
    }

    @Override
    public void delete(Long id) {
        Optional<Masterpiece> existingMasterpiece = repository.findById(id);
        if (existingMasterpiece.isPresent()) {
            repository.delete(id);
            return;
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("Masterpiece", id));
    }

    @Override
    public List<Masterpiece> getByArtistId(Long artistId) {
        Optional<Artist> existingArtist = artistRepository.findById(artistId);
        if (existingArtist.isPresent()) {
            return repository.findByArtistId(artistId);
        }
        throw new EntityNotFoundException(ExceptionMessageUtil.entityNotFound("Artist", artistId));
    }

}
