package interfaces;

import entities.Song;

@FunctionalInterface
public interface SongOperation {
    void operate(Song song);
}