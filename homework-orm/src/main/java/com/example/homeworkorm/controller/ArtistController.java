package com.example.homeworkorm.controller;

import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.service.ArtistService;

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
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllArtists() {
        List<Artist> artists = service.getAll();
        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Long id) {
        Artist artist = service.getById(id);
        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addArtist(@RequestBody Artist artist) {
        service.add(artist);
        return new ResponseEntity<>("Artist created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
        service.update(id, artist);
        return new ResponseEntity<>("Artist updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>("Artist deleted successfully", HttpStatus.OK);
    }

}
