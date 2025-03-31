import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages the music library and playback functionality, including song storage,
 * playback history, and shuffle mode.
 */
public class MusicStreamingService {

    private final Playlist library; // Main song library
    private final List<Song> playbackHistory; // Tracks recent plays
    private static final int HISTORY_LIMIT = 5; // Maximum number of songs in playback history

    /**
     * Initializes an empty music streaming service with a library named 'Music Library' and playback history.
     */
    public MusicStreamingService() {
        this.library = new Playlist("Music Library");
        this.playbackHistory = new ArrayList<>();
    }

    /**
     * Populates the library with 10 initial songs.
     */
    public void initialiseDefaultSongs() {
        library.addSong(new Song("Midnight Rain", "Taylor Swift", 12564321, 2022, "Pop"));
        library.addSong(new Song("Viva La Vida", "Coldplay", 892345678, 2008, "Rock"));
        library.addSong(new Song("Blinding Lights", "The Weeknd", 1567890123, 2019, "Synth-Pop"));
        library.addSong(new Song("Levitating", "Dua Lipa", 98765432, 2020, "Pop"));
        library.addSong(new Song("Golden Hour", "JVKE", 45678912, 2022, "Pop"));
        library.addSong(new Song("Unholy", "Sam Smith", 78912345, 2022, "Pop"));
        library.addSong(new Song("Flowers", "Miley Cyrus", 234567890, 2023, "Pop"));
        library.addSong(new Song("As It Was", "Harry Styles", 567890123, 2022, "Pop-Rock"));
        library.addSong(new Song("Anti-Hero", "Taylor Swift", 89123456, 2022, "Pop"));
        library.addSong(new Song("Heat Waves", "Glass Animals", 345678901, 2020, "Indie"));
    }

    /**
     * Adds a song to the library.
     * parameter 'song' specifies the song to add
     */
    public void addSong(Song song) {
        library.addSong(song);
    }

    /**
     * Removes a song from both the library and playback history.
     * parameter 'song' specifies the song to remove
     */
    public void removeSong(Song song) {
        library.removeSong(song);
        playbackHistory.remove(song); // Ensure consistency with history
    }

    /**
     * Plays a song at the specified index, incrementing its play count by 1 and updating the playbackHistory.
     * @param index The index of the song to play
     */
    public void playSong(int index) {
        List<Song> songs = library.getSongs();
        if (index >= 0 && index < songs.size()) {
            Song song = songs.get(index);
            song.incrementPlayCount();
            playbackHistory.add(0, song); // Add as most recent
            if (playbackHistory.size() > HISTORY_LIMIT) {
                playbackHistory.remove(HISTORY_LIMIT); // Trim to limit
            }
        }
    }

    /**
     * Returns a copy of the playback history.
     * returns a List of recently played songs
     */
    public List<Song> getPlaybackHistory() {
        return new ArrayList<>(playbackHistory);
    }

    /**
     * Toggles shuffle mode from 'ON' or 'OFF' for the library's song order.
     */
    public void toggleShuffle() {
        library.setShuffle(!library.isShuffled());
    }

    /**
     * Gets the current library playlist.
     * returns The library playlist
     */
    public Playlist getLibrary() {
        return library;
    }

    /**
     * Filters songs by minimum play count.
     * the intention is to enable a user to search for popular songs, utilising the required playCount attribute
     * returns a Collection of songs with play counts at or above the threshold
     */
    public Collection<Song> filterSongsByPlays(long minPlays) {
        return library.getSongs().stream()
                .filter(s -> s.getPlayCount() >= minPlays)
                .collect(Collectors.toList());
    }
}