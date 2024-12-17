package com.example.homeworkorm.service;

import com.example.homeworkorm.entity.ArtGallery;

import java.util.List;

public interface ArtGalleryService extends IService<ArtGallery> {

    List<ArtGallery> getByArtistId(Long artistId);

}
