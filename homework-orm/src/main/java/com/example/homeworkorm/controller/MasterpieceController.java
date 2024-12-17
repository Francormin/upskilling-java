package com.example.homeworkorm.controller;

import com.example.homeworkorm.entity.Masterpiece;
import com.example.homeworkorm.service.MasterpieceService;

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
@RequestMapping("/masterpieces")
public class MasterpieceController {

    private final MasterpieceService service;

    public MasterpieceController(MasterpieceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAllMasterpieces() {
        List<Masterpiece> masterpieces = service.getAll();
        return new ResponseEntity<>(masterpieces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMasterpieceById(@PathVariable Long id) {
        Masterpiece masterpiece = service.getById(id);
        return new ResponseEntity<>(masterpiece, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addMasterpiece(@RequestBody Masterpiece masterpiece) {
        service.add(masterpiece);
        return new ResponseEntity<>("Masterpiece created successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMasterpiece(@PathVariable Long id, @RequestBody Masterpiece masterpiece) {
        service.update(id, masterpiece);
        return new ResponseEntity<>("Masterpiece updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMasterpiece(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>("Masterpiece deleted successfully", HttpStatus.OK);
    }

    /**
     * Obtiene todas las obras de arte de un artista específico.
     *
     * @param artistId ID del artista.
     * @return Lista de obras de arte.
     */
    @GetMapping("/by-artist/{artistId}")
    public ResponseEntity<?> getMasterpiecesByArtistId(@PathVariable Long artistId) {
        List<Masterpiece> masterpieces = service.getByArtistId(artistId);
        return new ResponseEntity<>(masterpieces, HttpStatus.OK);
    }

}
