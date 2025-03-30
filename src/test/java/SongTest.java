import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SongTest {

    private Song song;

    @BeforeEach
    void setup() {
        //placeholder for setup implementation
    }

    @Test
    void testSongCreation(){
        song = new Song("Test Song", "Test Artist", 0, 2023, "Pop");
        assertEquals("Test Song", song.getTitle());
        assertEquals("Test Artist", song.getArtist());
        assertEquals(0, song.getPlayCount());
        assertEquals(2023, song.getYear());
        assertEquals("Pop", song.getGenre());
    }

    @Test
    void testInvalidTitleThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Song("", "Test Artist", 0, 2023, "Pop"));
    }

    @Test
    void testInvalidArtistThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Song("Test Song", "", 0, 2023, "Pop"));
    }

    @Test
    void testIncrementPlayCount() {
        song = new Song("Test Song", "Test Artist", 0, 2023, "Pop");
        song.incrementPlayCount();
        assertEquals(1, song.getPlayCount());
    }

    @Test
    void testToStringFormat() {
        song = new Song("Test Song", "Test Artist", 1000, 2023, "Pop");
        String expected = "Test Song                    by Test Artist 1000 (2023)    Pop";
        assertEquals(expected, song.toString());
    }

}
