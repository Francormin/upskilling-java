package com.example.homeworkrest.service;

import com.example.homeworkrest.model.Song;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private List<Song> songs = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private File songsFile;

    @PostConstruct
    public void init() {
        try {
            songsFile = new ClassPathResource("songs.json").getFile();
            songs = objectMapper.readValue(songsFile, new TypeReference<List<Song>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Song> getAll() {
        return songs;
    }

    public Optional<Song> getById(int id) {
        return songs.stream()
            .filter(song -> song.getId() == id)
            .findFirst();
    }

    public Song create(Song song) {
        songs.add(song);
        saveToFile();
        return song;
    }

    public Song update(Song songToUpdate, Song songDetails) {
        songToUpdate.setTitle(songDetails.getTitle());
        songToUpdate.setArtist(songDetails.getArtist());
        songToUpdate.setReleaseYear(songDetails.getReleaseYear());
        saveToFile();
        return songToUpdate;
    }

    public void delete(Song song) {
        songs.remove(song);
        saveToFile();
    }

    private void saveToFile() {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(songsFile, songs);
            System.out.println("Songs saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
