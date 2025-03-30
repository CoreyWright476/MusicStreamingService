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
    private ByteArrayInputStream inContent;

    @BeforeEach
    void setUp() {
        service = new MusicStreamingService();
        service.initialiseDefaultSongs();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    private void setInput(String input) {
        inContent = new ByteArrayInputStream(input.getBytes());
        app = new MusicStreamingApplication(new Scanner(inContent), service);
    }

    @Test
    void testPlaySongValidInput() {
        setInput("1\n");
        Song songBefore = service.getLibrary().getSongs(false).get(0);
        long initialPlayCount = songBefore.getPlayCount();
        app.playSong();
        String output = outContent.toString();
        assertTrue(output.contains("Playing: "));
        assertTrue(output.contains(songBefore.getTitle()));
        assertEquals(initialPlayCount + 1, songBefore.getPlayCount());
    }

    @Test
    void testRunProcessesPlaySong() {
        setInput("5\n1\n6\n");
        Song songBefore = service.getLibrary().getSongs(false).get(0);
        long initialPlayCount = songBefore.getPlayCount();
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Playing: "));
        assertTrue(output.contains(songBefore.getTitle()));
        assertEquals(initialPlayCount + 1, songBefore.getPlayCount());
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunExitsOnExit() {
        setInput("6\n"); // Updated to 6
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    // Update other run tests to use "6" instead of "5" for exit
    @Test
    void testRunProcessesAddSong() {
        setInput("1\nTest Song\nTest Artist\n500\n2024\nPop\n6\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Added: Test Song                    by Test Artist 500 (2024)    Pop"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesRemoveSong() {
        setInput("2\n1\n6\n");
        Song songToRemove = service.getLibrary().getSongs(false).get(0);
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Removed: " + songToRemove));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesListSongs() {
        setInput("3\n6\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Music Library (10 songs)"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesListSongsByPlayCount() {
        setInput("4\n100000000\n6\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Songs with 100000000 or more plays:"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunHandlesInvalidChoice() {
        setInput("x\n6\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Invalid choice. Try again."));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }
}