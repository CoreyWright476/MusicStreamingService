import java.util.*;
import java.util.stream.Collectors;

public class MusicStreamingService {
    private final Playlist library;
    private final Set<Song> songLibrary;
    private final Map<String, Playlist> playlists;

    public MusicStreamingService() {
        this.library = new Playlist("Music Library");
        this.songLibrary = new TreeSet<>(Comparator.comparing(Song::getTitle)
                .thenComparing(Song::getArtist).thenComparing(Song::getYear));
        this.playlists = new TreeMap<>();
    }

    public void initialiseDefaultSongs() {
        if (songLibrary.isEmpty()) {
            songLibrary.add(new Song("Midnight Rain", "Taylor Swift", 12564321, 2022, "Pop"));
            songLibrary.add(new Song("Viva La Vida", "Coldplay", 892345678, 2008, "Rock"));
            songLibrary.add(new Song("Blinding Lights", "The Weeknd", 1567890123, 2019, "Synth-Pop"));
            songLibrary.add(new Song("Levitating", "Dua Lipa", 98765432, 2020, "Pop"));
            songLibrary.add(new Song("Golden Hour", "JVKE", 45678912, 2022, "Pop"));
            songLibrary.add(new Song("Unholy", "Sam Smith", 78912345, 2022, "Pop"));
            songLibrary.add(new Song("Flowers", "Miley Cyrus", 234567890, 2023, "Pop"));
            songLibrary.add(new Song("As It Was", "Harry Styles", 567890123, 2022, "Pop-Rock"));
            songLibrary.add(new Song("Anti-Hero", "Taylor Swift", 89123456, 2022, "Pop"));
            songLibrary.add(new Song("Heat Waves", "Glass Animals", 345678901, 2020, "Indie"));
            songLibrary.forEach(library::addSong);
            playlists.put("Favorites", new Playlist("Favorites"));
        }
    }

    public void addSong(Song song) {
        songLibrary.add(song);
        library.addSong(song);
    }

    public void removeSong(Song song) {
        songLibrary.remove(song);
        playlists.values().forEach(p -> p.removeSong(song));
        library.removeSong(song);
    }

    public void playSong(Song song) {
        if (songLibrary.contains(song)) {
            song.incrementPlayCount();
        }
    }

    public void playSong(int index) {
        Song song = library.getSongs(false).get(index);
        song.incrementPlayCount();
    }

    public Collection<Song> filterSongsByPlays(long minPlays) {
        return songLibrary.stream()
                .filter(s -> s.getPlayCount() >= minPlays)
                .collect(Collectors.toList());
    }

    public Playlist getLibrary() {
        return library;
    }

    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }
}