import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Song class, verifying the Song construction and behaviour.
 */
public class SongTest {

    private Song song;

    /** Sets up a sample song before each test. */
    @BeforeEach
    void setup() {
        song = new Song("Test Song", "Test Artist", 0, 2023, "Pop");
    }

    /** Tests successful song creation with valid data. */
    @Test
    void testSongCreation() {
        assertEquals("Test Song", song.getTitle());
        assertEquals("Test Artist", song.getArtist());
        assertEquals(0, song.getPlayCount());
        assertEquals(2023, song.getYear());
        assertEquals("Pop", song.getGenre());
    }

    /** Tests that an empty title throws an exception. */
    @Test
    void testInvalidTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Song("", "Test Artist", 0, 2023, "Pop"));
    }

    /** Tests that an empty artist throws an exception. */
    @Test
    void testInvalidArtistThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Song("Test Song", "", 0, 2023, "Pop"));
    }

    /** Tests incrementing the play count. */
    @Test
    void testIncrementPlayCount() {
        song.incrementPlayCount();

        assertEquals(1, song.getPlayCount());
    }

    /** Tests the formatted string representation of the song. */
    @Test
    void testToStringFormat() {
        String expected = "Test Song                    by Test Artist 0 (2023)    Pop";

        assertEquals(expected, song.toString());
    }
}