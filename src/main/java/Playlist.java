import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a playlist or of songs, supporting addition, removal, and shuffle functionality. This is intended to store the songs and manipulate the list of them
 */
public class Playlist {

    private final String name; // Playlist name
    private final List<Song> songs; // Original song list
    private boolean shuffle; // Shuffle mode status
    private List<Song> currentSongs; // Current display order (shuffled or original)

    /**
     * Creates a new playlist with the given name.
     * name is the name of the playlist
     * the constructor ensures to throw a IllegalArgumentException if name is null or empty
     */
    public Playlist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Playlist name cannot be null or empty");
        }
        this.name = name;
        this.songs = new ArrayList<>();
        this.shuffle = false;
        this.currentSongs = new ArrayList<>(songs);
    }

    /**
     * Adds a song to the playlist if it’s not already present.
     * parameter 'song' specifies The song to add
     */
    public void addSong(Song song) {
        if (song != null && !songs.contains(song)) {
            songs.add(song);
            updateCurrentSongs();
        }
    }

    /**
     * Removes a song from the playlist and updates the current order.
     * parameter 'song' specifies The song to remove
     */
    public void removeSong(Song song) {
        if (songs.remove(song)) {
            updateCurrentSongs();
        }
    }

    /**
     * Returns a copy of the current song list, reflecting shuffle state.
     * returns a List of songs in current order
     */
    public List<Song> getSongs() {
        return new ArrayList<>(currentSongs);
    }

    /**
     * Sets shuffle to ON(true) or OFF(false).
     * parameter 'shuffle' is a boolean set to true to enable shuffle, false to revert to original order
     * the intention is to randomise the order of the playlist to imitate a real world shuffle feature
     */
    public void setShuffle(boolean shuffle) {
        if (this.shuffle != shuffle) {
            this.shuffle = shuffle;
            updateCurrentSongs();
        }
    }

    /**
     * Checks if shuffle mode is enabled.
     * returns true if shuffled, false otherwise
     */
    public boolean isShuffled() {
        return shuffle;
    }

    /**
     * Gets the playlist name.
     * returns the name of the playlist
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of songs in the playlist.
     * returns size of the song list
     */
    public int size() {
        return songs.size();
    }

    /**
     * Updates the current song list based on shuffle state.
     */
    private void updateCurrentSongs() {
        currentSongs = new ArrayList<>(songs);
        if (shuffle) {
            Collections.shuffle(currentSongs);
        }
    }

    /**
     * Returns a string representation of the playlist, indicating size and shuffle state.
     * returns a playlist description
     */
    @Override
    public String toString() {
        String base = name + " (" + songs.size() + " songs)";
        return shuffle ? base + ", shuffled" : base;
    }
}