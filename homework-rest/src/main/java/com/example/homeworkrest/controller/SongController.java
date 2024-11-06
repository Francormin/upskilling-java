package com.example.homeworkrest.controller;

import com.example.homeworkrest.model.Song;
import com.example.homeworkrest.service.SongService;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<List<Song>> getAll() {
        List<Song> songs = songService.getAll();
        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Song>> getById(@PathVariable int id) {
        Optional<Song> song = songService.getById(id);
        if (song.isPresent()) {
            return new ResponseEntity<>(song, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Song> create(@RequestBody Song song) {
        Song createdSong = songService.create(song);
        return new ResponseEntity<>(createdSong, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> update(@PathVariable int id, @RequestBody Song songDetails) {
        Optional<Song> song = songService.getById(id);
        if (song.isPresent()) {
            Song songToUpdate = song.get();
            Song updatedSong = songService.update(songToUpdate, songDetails);
            return new ResponseEntity<>(updatedSong, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Song> song = songService.getById(id);
        if (song.isPresent()) {
            Song songToDelete = song.get();
            songService.delete(songToDelete);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
