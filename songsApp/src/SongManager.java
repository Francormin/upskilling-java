import entities.Song;
import interfaces.SongOperation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SongManager {
    public static void main(String[] args) {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("Song 1", "Artist A", "Pop", LocalDate.of(2022, 3, 10)));
        songs.add(new Song("Song 2", "Artist B", "Rock", LocalDate.of(2021, 5, 20)));
        songs.add(new Song("Song 3", "Artist A", "Pop", LocalDate.of(2023, 1, 15)));

        SongOperation printSong = song -> System.out.println(song);

        System.out.println("FORMA IMPERATIVA");
        for (Song song : songs) {
            System.out.println(song);
        }

        System.out.println();
        System.out.println("USANDO LA LISTA");
        songs.forEach(printSong::operate);

        System.out.println();
        System.out.println("Canciones agrupadas por género:");

        Map<String, List<Song>> songsByGenre = songs.stream()
                .collect(Collectors.groupingBy(Song::getGenre));

        songsByGenre.forEach((genre, songsInGenre) -> {
            System.out.println("Género: " + genre);
            songsInGenre.forEach(song -> System.out.println("- " + song));
        });

        System.out.println();
        System.out.println("Canciones ordenadas por fecha de lanzamiento:");
        songs.stream()
                .sorted(Comparator.comparing(Song::getReleaseDate))
                .forEach(printSong::operate);
    }
}