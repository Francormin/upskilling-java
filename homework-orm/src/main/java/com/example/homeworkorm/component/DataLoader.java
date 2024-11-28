package com.example.homeworkorm.component;

import com.example.homeworkorm.entity.ArtGallery;
import com.example.homeworkorm.entity.Artist;
import com.example.homeworkorm.entity.Masterpiece;
import com.example.homeworkorm.repository.ArtGalleryRepository;
import com.example.homeworkorm.repository.ArtistRepository;
import com.example.homeworkorm.repository.MasterpieceRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final ArtistRepository artistRepository;
    private final MasterpieceRepository masterpieceRepository;
    private final ArtGalleryRepository artGalleryRepository;

    public DataLoader(
        ArtistRepository artistRepository,
        MasterpieceRepository masterpieceRepository,
        ArtGalleryRepository artGalleryRepository) {

        this.artistRepository = artistRepository;
        this.masterpieceRepository = masterpieceRepository;
        this.artGalleryRepository = artGalleryRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        // Crear artistas
        Artist picasso = new Artist();
        picasso.setName("Pablo Picasso");
        picasso.setSpecialty("Cubismo");
        picasso.setBio("Pintor y escultor español, una de las figuras más destacadas del arte del siglo XX");
        artistRepository.save(picasso);

        Artist vanGogh = new Artist();
        vanGogh.setName("Vincent van Gogh");
        vanGogh.setSpecialty("Postimpresionismo");
        vanGogh.setBio("Pintor neerlandés, una de las figuras más grandes de la pintura occidental");
        artistRepository.save(vanGogh);

        Artist monet = new Artist();
        monet.setName("Claude Monet");
        monet.setSpecialty("Impresionismo");
        monet.setBio("Pintor francés, considerado uno de los padres del Impresionismo");
        artistRepository.save(monet);

        // Crear obras de arte
        Masterpiece guernica = new Masterpiece();
        guernica.setTitle("Guernica");
        guernica.setGenre("Pintura");
        guernica.setCreationYear(1937);
        guernica.setArtist(picasso);
        picasso.getMasterpieces().add(guernica);
        masterpieceRepository.save(guernica);

        Masterpiece nocheEstrellada = new Masterpiece();
        nocheEstrellada.setTitle("La noche estrellada");
        nocheEstrellada.setGenre("Pintura");
        nocheEstrellada.setCreationYear(1889);
        nocheEstrellada.setArtist(vanGogh);
        vanGogh.getMasterpieces().add(nocheEstrellada);
        masterpieceRepository.save(nocheEstrellada);

        Masterpiece impresionSolNaciente = new Masterpiece();
        impresionSolNaciente.setTitle("Impresión, sol naciente");
        impresionSolNaciente.setGenre("Pintura");
        impresionSolNaciente.setCreationYear(1872);
        impresionSolNaciente.setArtist(monet);
        monet.getMasterpieces().add(impresionSolNaciente);
        masterpieceRepository.save(impresionSolNaciente);

        // Crear galerías de arte
        ArtGallery museoPrado = new ArtGallery();
        museoPrado.setName("Museo del Prado");
        museoPrado.setDate("27-11-2024");
        artGalleryRepository.save(museoPrado);

        ArtGallery museoModerno = new ArtGallery();
        museoModerno.setName("Museo de Arte Moderno");
        museoModerno.setDate("27-11-2024");
        artGalleryRepository.save(museoModerno);

        ArtGallery museoOrsay = new ArtGallery();
        museoOrsay.setName("Museo de Orsay");
        museoOrsay.setDate("27-11-2024");
        artGalleryRepository.save(museoOrsay);

        // Relacionar galerías con obras
        museoPrado.getMasterpieces().addAll(Arrays.asList(guernica, nocheEstrellada));
        museoModerno.getMasterpieces().add(impresionSolNaciente);
        museoOrsay.getMasterpieces().add(guernica);
        artGalleryRepository.saveAll(Arrays.asList(museoPrado, museoModerno, museoOrsay));

        guernica.getArtGalleries().addAll(Arrays.asList(museoPrado, museoOrsay));
        nocheEstrellada.getArtGalleries().add(museoPrado);
        impresionSolNaciente.getArtGalleries().add(museoModerno);

    }

}
