package com.example.homeworkorm.controller;

import com.example.homeworkorm.entity.ArtGallery;
import com.example.homeworkorm.service.ArtGalleryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/art-galleries")
public class ArtGalleryController {

    private final ArtGalleryService service;

    public ArtGalleryController(ArtGalleryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllArtGalleries() {
        List<ArtGallery> artGalleries = service.getAll();
        return new ResponseEntity<>(artGalleries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtGalleryById(@PathVariable Long id) {
        ArtGallery artGallery = service.getById(id);
        return new ResponseEntity<>(artGallery, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addArtGallery(@RequestBody ArtGallery artGallery) {
        service.add(artGallery);
        return new ResponseEntity<>("ArtGallery created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArtGallery(@PathVariable Long id, @RequestBody ArtGallery artGallery) {
        service.update(id, artGallery);
        return new ResponseEntity<>("ArtGallery updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtGallery(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>("ArtGallery deleted successfully", HttpStatus.OK);
    }

    /**
     * Obtiene todas las galerías de arte en las que exhibe un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de galerías de arte.
     */
    @GetMapping("/by-artist/{artistId}")
    public ResponseEntity<?> getArtGalleriesByArtistId(@PathVariable Long artistId) {
        List<ArtGallery> artGalleries = service.getByArtistId(artistId);
        return new ResponseEntity<>(artGalleries, HttpStatus.OK);
    }

}
