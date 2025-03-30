import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class MusicStreamingApplicationTest {
    private MusicStreamingApplication app;
    private MusicStreamingService service;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        service = new MusicStreamingService();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    private void setInput(String input) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        app = new MusicStreamingApplication(new Scanner(inContent), service);
    }

    @Test
    void testPlaySongValidInput() {
        setInput("5\n1\n8\n");
        service.initialiseDefaultSongs();
        Song songBefore = service.getLibrary().getSongs().get(0);
        long initialPlayCount = songBefore.getPlayCount();
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Playing: "));
        assertTrue(output.contains(songBefore.getTitle()));
        assertEquals(initialPlayCount + 1, songBefore.getPlayCount());
    }

    @Test
    void testRunProcessesPlaySong() {
        setInput("5\n1\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Playing: "));
        assertTrue(output.contains("Midnight Rain"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunExitsOnExit() {
        setInput("8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesAddSong() {
        setInput("1\nTest Song\nTest Artist\n500\n2023\nPop\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Added: Test Song                    by Test Artist 500 (2023)    Pop"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesRemoveSong() {
        setInput("2\n1\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Removed: Midnight Rain"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesListSongs() {
        setInput("3\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Music Library (10 songs)"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesListSongsByPlayCount() {
        setInput("4\n100000000\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Songs with 100000000 or more plays:"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunHandlesInvalidChoice() {
        setInput("x\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Invalid choice. Try again."));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testToggleShuffle() {
        setInput("6\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Shuffle mode is now ON"));
    }

    @Test
    void testShowPlaybackHistory() {
        setInput("5\n1\n7\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Playing: Midnight Rain"));
        assertTrue(output.contains("Playback History"));
        assertTrue(output.contains("Midnight Rain"));
    }

    @Test
    void testAddSongWithEmptyFields() {
        // Empty title, then valid title, empty artist, then valid artist
        setInput("1\n\nValid Title\n\nValid Artist\n500\n2023\nPop\n8\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Title cannot be empty. Try again."));
        assertTrue(output.contains("Artist cannot be empty. Try again."));
        assertTrue(output.contains("Added: Valid Title                  by Valid Artist 500 (2023)    Pop"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }
}