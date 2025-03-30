import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
    private final String name;
    private final List<Song> songs;
    private boolean shuffle;

    public Playlist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be null or empty");
        }
        this.name = name;
        this.songs = new ArrayList<>();
        this.shuffle = false;
    }

    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
        }
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public List<Song> getSongs(boolean applyShuffle) {
        if (applyShuffle && shuffle) {
            List<Song> shuffled = new ArrayList<>(songs);
            Collections.shuffle(shuffled);
            return shuffled;
        }
        return new ArrayList<>(songs); // Return a copy to prevent external modification
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return songs.size();
    }

    @Override
    public String toString() {
        String base = name + " (" + songs.size() + " songs)";
        return shuffle ? base + ", shuffled" : base;
    }
}