import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {

    private Playlist playlist;
    private Song song;

    @BeforeEach
    public void setUp() {
        playlist = new Playlist("Test Playlist");
        song = new Song("Test Song", "Test artist", 0, 2023, "Pop");
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
        playlist.addSong(song);
        assertEquals(1, playlist.size());
        assertTrue(playlist.getSongs(false).contains(song));
    }

    @Test
    void testShuffle() {
        Song song2 = new Song("Test Song 2", "Test artist 2", 0, 2023, "Rock");
        playlist.addSong(song);
        playlist.addSong(song2);
        playlist.getShuffle(true);
        List<Song> unshuffled = playlist.getSongs(false);
        List<Song> shuffled = playlist.getSongs(true);
        assertEquals(2, unshuffled.size());
        assertTrue(shuffled.contains(song) && shuffled.contains(song2));
    }

    @Test
    void testToString() {
        playlist.addSong(song);
        String expected = "Test Playlist (1 song)";
        assertEquals(expected, playlist.toString);
        playlist.setShuffle(true);
        expected = "Test playlist(1 song, shuffled)";
        assertEquals(expected, playlist.toString);
    }

}
