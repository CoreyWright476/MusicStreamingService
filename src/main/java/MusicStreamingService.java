import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MusicStreamingService {
    private final Playlist library;
    private final List<Song> playbackHistory;

    public MusicStreamingService() {
        this.library = new Playlist("Music Library");
        this.playbackHistory = new ArrayList<>();
    }

    public void initialiseDefaultSongs() {
        // (Default songs initialization remains unchanged)
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

    public void addSong(Song song) {
        library.addSong(song);
    }

    public void removeSong(Song song) {
        library.removeSong(song);
        playbackHistory.remove(song); // Remove from history if present
    }

    public void playSong(int index) {
        List<Song> songs = library.getSongs();
        if (index >= 0 && index < songs.size()) {
            Song song = songs.get(index);
            song.incrementPlayCount();
            playbackHistory.add(0, song); // Add to the beginning (most recent)
            if (playbackHistory.size() > 5) {
                playbackHistory.remove(5); // Keep only the last 5
            }
        }
    }

    public List<Song> getPlaybackHistory() {
        return new ArrayList<>(playbackHistory);
    }

    public void toggleShuffle() {
        library.setShuffle(!library.isShuffled());
    }

    public Playlist getLibrary() {
        return library;
    }

    public Collection<Song> filterSongsByPlays(long minPlays) {
        return library.getSongs().stream()
                .filter(s -> s.getPlayCount() >= minPlays)
                .collect(Collectors.toList());
    }
}