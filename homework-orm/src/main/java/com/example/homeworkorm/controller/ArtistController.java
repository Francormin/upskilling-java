package com.example.homeworkorm.controller;

import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.service.impl.ArtistService;

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
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAll();
    }

    @GetMapping("/{id}")
    public Artist getArtistById(@PathVariable Long id) {
        return artistService.getById(id);
    }

    @PostMapping
    public void addArtist(@RequestBody Artist artist) {
        artistService.add(artist);
    }

    @PutMapping("/{id}")
    public void updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
        artistService.update(id, artist);
    }

    @DeleteMapping("/{id}")
    public void deleteArtist(@PathVariable Long id) {
        artistService.delete(id);
    }

}
