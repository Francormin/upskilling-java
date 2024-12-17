package com.example.homeworkorm.service;

import com.example.homeworkorm.entity.Masterpiece;

import java.util.List;

public interface MasterpieceService extends IService<Masterpiece> {

    List<Masterpiece> getByArtistId(Long artistId);

}
