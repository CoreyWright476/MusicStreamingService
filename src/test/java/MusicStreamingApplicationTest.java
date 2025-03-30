import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class MusicStreamingApplicationTest {

    private MusicStreamingApplication app;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        app = new MusicStreamingApplication();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testInitialiseDefaultSongs() {
        app.initialiseDefaultSongs();
        assertEquals(10, app.getSongLibrarySize());
        assertEquals(1, app.getPlaylistsSize());
    }

    @Test
    void testRemoveSong() {
        Song song = new Song("New Song", "New Artist", 0, 2024, "Pop");
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
        Song song = new Song("New Song", "New Artist", 0, 2024, "Pop");
        app.addSong(song);
        app.playSong(song);
        assertEquals(1, song.getPlayCount());
    }

}
