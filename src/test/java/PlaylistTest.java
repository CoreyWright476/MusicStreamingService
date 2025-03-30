import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    private Playlist playlist;
    private Song song1;
    private Song song2;

    @BeforeEach
    public void setUp() {
        playlist = new Playlist("Test Playlist");
        song1 = new Song("Test Song", "Test Artist", 0, 2023, "Pop");
        song2 = new Song("Another Song", "Another Artist", 0, 2022, "Rock");
    }

    @Test
    void testPlaylistCreation() {
        assertEquals("Test Playlist", playlist.getName());
        assertEquals(0, playlist.size());
    }

    @Test
    void invalidNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Playlist(""));
    }

    @Test
    void testAddSong() {
        playlist.addSong(song1);
        assertEquals(1, playlist.size());
        assertTrue(playlist.getSongs().contains(song1));
    }

    @Test
    void testAddDuplicateSong() {
        playlist.addSong(song1);
        playlist.addSong(song1);
        assertEquals(1, playlist.size());
    }

    @Test
    void testRemoveSong() {
        playlist.addSong(song1);
        playlist.removeSong(song1);
        assertEquals(0, playlist.size());
        assertFalse(playlist.getSongs().contains(song1));
    }

    // New tests for shuffle
    @Test
    void testSetShuffle() {
        playlist.addSong(song1);
        playlist.addSong(song2);
        playlist.setShuffle(true);
        assertTrue(playlist.isShuffled());
        assertEquals(2, playlist.getSongs().size());
        playlist.setShuffle(false);
        assertFalse(playlist.isShuffled());
        assertEquals(song1, playlist.getSongs().get(0)); // Order preserved
    }

    @Test
    void testToStringWithShuffle() {
        playlist.addSong(song1);
        assertEquals("Test Playlist (1 songs)", playlist.toString());
        playlist.setShuffle(true);
        assertEquals("Test Playlist (1 songs), shuffled", playlist.toString());
    }
}