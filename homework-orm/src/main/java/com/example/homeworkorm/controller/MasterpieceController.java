package com.example.homeworkorm.controller;

import com.example.homeworkorm.entity.Masterpiece;
import com.example.homeworkorm.service.impl.MasterpieceService;

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
@RequestMapping("/masterpieces")
public class MasterpieceController {

    private final MasterpieceService masterpieceService;

    public MasterpieceController(MasterpieceService masterpieceService) {
        this.masterpieceService = masterpieceService;
    }

    @GetMapping
    public List<Masterpiece> getAllMasterpieces() {
        return masterpieceService.getAll();
    }

    @GetMapping("/{id}")
    public Masterpiece getMasterpieceById(@PathVariable Long id) {
        return masterpieceService.getById(id);
    }

    @PostMapping
    public void addMasterpiece(@RequestBody Masterpiece masterpiece) {
        masterpieceService.add(masterpiece);
    }

    @PutMapping("/{id}")
    public void updateMasterpiece(@PathVariable Long id, @RequestBody Masterpiece masterpiece) {
        masterpieceService.update(id, masterpiece);
    }

    @DeleteMapping("/{id}")
    public void deleteMasterpiece(@PathVariable Long id) {
        masterpieceService.delete(id);
    }

    /**
     * Obtiene todas las obras de arte de un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de obras de arte.
     */
    @GetMapping("/by-artist/{artistId}")
    public ResponseEntity<?> getMasterpiecesByArtist(@PathVariable Long artistId) {
        List<Masterpiece> masterpieces = masterpieceService.getMasterpiecesByArtist(artistId);
        return ResponseEntity.ok(masterpieces);
    }

}
