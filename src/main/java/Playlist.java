import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {
    private final String name;
    private final List<Song> songs;
    private boolean shuffle;
    private List<Song> currentSongs; // Reflects the current song order (shuffled or not)

    public Playlist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be null or empty");
        }
        this.name = name;
        this.songs = new ArrayList<>();
        this.shuffle = false;
        this.currentSongs = new ArrayList<>(songs);
    }

    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
            updateCurrentSongs();
        }
    }

    public void removeSong(Song song) {
        if (songs.remove(song)) {
            updateCurrentSongs();
        }
    }

    public List<Song> getSongs() {
        return new ArrayList<>(currentSongs);
    }

    public void setShuffle(boolean shuffle) {
        if (this.shuffle != shuffle) {
            this.shuffle = shuffle;
            updateCurrentSongs();
        }
    }

    public boolean isShuffled() {
        return shuffle;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return songs.size();
    }

    private void updateCurrentSongs() {
        currentSongs = new ArrayList<>(songs);
        if (shuffle) {
            Collections.shuffle(currentSongs);
        }
    }

    @Override
    public String toString() {
        String base = name + " (" + songs.size() + " songs)";
        return shuffle ? base + ", shuffled" : base;
    }
}