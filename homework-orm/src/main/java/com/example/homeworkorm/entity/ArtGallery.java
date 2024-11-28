package com.example.homeworkorm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "art_galleries")
public class ArtGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    private String date;

    @ManyToMany
    @JoinTable(
        name = "gallery_masterpiece",
        joinColumns = @JoinColumn(name = "art_gallery_id"),
        inverseJoinColumns = @JoinColumn(name = "masterpiece_id")
    )
    private List<Masterpiece> masterpieces = new ArrayList<>();

}
