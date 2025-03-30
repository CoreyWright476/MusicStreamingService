import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MusicStreamingService class, verifying song management and features.
 */
public class MusicStreamingServiceTest {

    private MusicStreamingService service;

    /** Sets up a fresh service instance before each test. */
    @BeforeEach
    void setUp() {
        service = new MusicStreamingService();
    }

    /** Tests initialization of default songs. */
    @Test
    void testInitialiseDefaultSongs() {
        service.initialiseDefaultSongs();

        assertEquals(10, service.getLibrary().size());
    }

    /** Tests adding a new song to the library. */
    @Test
    void testAddSong() {
        Song song = new Song("New Song", "New Artist", 0, 2023, "Jazz");

        service.addSong(song);

        assertTrue(service.getLibrary().getSongs().contains(song));
    }

    /** Tests removing a song from the library. */
    @Test
    void testRemoveSong() {
        service.initialiseDefaultSongs();
        Song song = service.getLibrary().getSongs().get(0);
        service.removeSong(song);

        assertFalse(service.getLibrary().getSongs().contains(song));
    }

    /** Tests filtering songs by play count. */
    @Test
    void testFilterSongsByPlays() {
        service.initialiseDefaultSongs();

        List<Song> filtered = (List<Song>) service.filterSongsByPlays(100000000);
        assertFalse(filtered.isEmpty());

        filtered.forEach(s -> assertTrue(s.getPlayCount() >= 100000000));
    }

    /** Tests playing a song and incrementing its play count. */
    @Test
    void testPlaySong() {
        service.initialiseDefaultSongs();

        Song song = service.getLibrary().getSongs().get(0);
        long initialPlayCount = song.getPlayCount();
        service.playSong(0);

        assertEquals(initialPlayCount + 1, song.getPlayCount());
    }

    /** Tests toggling shuffle mode on and off. */
    @Test
    void testToggleShuffle() {
        service.initialiseDefaultSongs();

        service.toggleShuffle();
        assertTrue(service.getLibrary().isShuffled());

        service.toggleShuffle();
        assertFalse(service.getLibrary().isShuffled());
    }

    /** Tests adding songs to playback history. */
    @Test
    void testPlaybackHistory() {
        service.initialiseDefaultSongs();
        service.playSong(0);
        service.playSong(1);

        List<Song> history = service.getPlaybackHistory();

        assertEquals(2, history.size());
        assertEquals(service.getLibrary().getSongs().get(1), history.get(0)); // Most recent first
    }

    /** Tests playback history limit enforcement. */
    @Test
    void testPlaybackHistoryLimit() {
        service.initialiseDefaultSongs();
        for (int i = 0; i < 6; i++) {
            service.playSong(i % service.getLibrary().size());
        }
        assertEquals(5, service.getPlaybackHistory().size());
    }
}