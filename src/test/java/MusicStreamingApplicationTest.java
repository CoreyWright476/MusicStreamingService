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
    void testDisplayMenu() {
        setInput("q\n");
        app.displayMenu();
        String output = outContent.toString();
        assertTrue(output.contains("=== Music Streaming App ==="));
        assertTrue(output.contains("1. List Songs"));
        assertTrue(output.contains("2. Play Song"));
        assertTrue(output.contains("q. Quit"));
        assertTrue(output.contains("Choice: "));
    }

    @Test
    void testListSongs() {
        setInput("q\n");
        app.listSongs();
        String output = outContent.toString();
        System.out.println("testListSongs output: [" + output + "]"); // Debug
        assertTrue(output.contains("Music Library (10 songs)"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("1. "));
        assertTrue(output.contains("10. "));
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
    void testPlaySongInvalidInput() {
        setInput("invalid\n");
        app.playSong();
        String output = outContent.toString();
        assertTrue(output.contains("Invalid selection: "));
        assertTrue(output.contains("For input string: \"invalid\"") || output.contains("NumberFormatException"));
    }

    @Test
    void testRunExitsOnQuit() {
        setInput("q\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesListSongs() {
        setInput("1\nq\n");
        app.run();
        String output = outContent.toString();
        System.out.println("testRunProcessesListSongs output: [" + output + "]"); // Debug
        assertTrue(output.contains("Music Library (10 songs)"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    @Test
    void testRunProcessesPlaySong() {
        setInput("2\n1\nq\n");
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
    void testRunHandlesInvalidChoice() {
        setInput("x\nq\n");
        app.run();
        String output = outContent.toString();
        assertTrue(output.contains("Invalid choice. Try again."));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }
}