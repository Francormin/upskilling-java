package com.example.homeworkorm.controller;

import com.example.homeworkorm.entity.ArtGallery;
import com.example.homeworkorm.service.impl.ArtGalleryService;

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
@RequestMapping("/artGalleries")
public class ArtGalleryController {

    private final ArtGalleryService artGalleryService;

    public ArtGalleryController(ArtGalleryService artGalleryService) {
        this.artGalleryService = artGalleryService;
    }

    @GetMapping
    public List<ArtGallery> getAllArtGalleries() {
        return artGalleryService.getAll();
    }

    @GetMapping("/{id}")
    public ArtGallery getArtGalleryById(@PathVariable Long id) {
        return artGalleryService.getById(id);
    }

    @PostMapping
    public void addArtGallery(@RequestBody ArtGallery artGallery) {
        artGalleryService.add(artGallery);
    }

    @PutMapping("/{id}")
    public void updateArtGallery(@PathVariable Long id, @RequestBody ArtGallery artGallery) {
        artGalleryService.update(id, artGallery);
    }

    @DeleteMapping("/{id}")
    public void deleteArtGallery(@PathVariable Long id) {
        artGalleryService.delete(id);
    }

    /**
     * Obtiene todas las galerías de arte en las que exhibe un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de galerías de arte.
     */
    @GetMapping("/by-artist/{artistId}")
    public List<ArtGallery> getArtGalleriesByArtist(@PathVariable Long artistId) {
        return artGalleryService.getArtGalleriesByArtist(artistId);
    }

}
