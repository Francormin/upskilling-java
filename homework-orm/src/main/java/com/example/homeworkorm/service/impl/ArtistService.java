package com.example.homeworkorm.service.impl;

import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.repository.ArtistRepository;

import org.springframework.stereotype.Service;

@Service
public class ArtistService extends AbstractService<Artist, ArtistRepository> {

    public ArtistService(ArtistRepository repository) {
        super(repository, "Artist");
    }

}
