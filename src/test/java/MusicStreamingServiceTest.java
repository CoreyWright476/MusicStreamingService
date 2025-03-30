import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;

public class MusicStreamingServiceTest {

    private MusicStreamingService app;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        app = new MusicStreamingService();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInitialiseDefaultSongs() {
        app.initialiseDefaultSongs();
        assertEquals(10, app.getSongLibrarySize());
        assertEquals(1, app.getPlaylistsSize());
    }

    @Test
    void testAddSong() {
        Song song = new Song("New Song", "New Artist", 0, 2023, "Jazz");
        app.addSong(song);
        assertTrue(app.containsSong(song));
    }

    @Test
    void testRemoveSong() {
        Song song = new Song("New Song", "New Artist", 0, 2023, "Jazz");
        app.addSong(song);
        app.removeSong(song);
        assertFalse(app.containsSong(song));
    }

    @Test
    void testFilterSongsByPlays() {
        app.initialiseDefaultSongs();
        Collection<Song> filtered = app.filterSongsByPlays(100000000);
        assertTrue(filtered.size() > 0);
        filtered.forEach(s -> assertTrue(s.getPlayCount() >= 100000000));
    }

    @Test
    void testPlaySong() {
        Song song = new Song("New Song", "New Artist", 0, 2023, "Jazz");
        app.addSong(song);
        app.playSong(song);
        assertEquals(1, song.getPlayCount());
    }
}