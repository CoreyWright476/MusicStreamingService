import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MusicStreamingApplication class, verifying all menu functionalities.
 */
public class MusicStreamingApplicationTest {
    private MusicStreamingApplication app;
    private MusicStreamingService service;
    private ByteArrayOutputStream outContent;
    private static final String EXIT_OPTION = "8"; // Matches application's exit constant

    /** Sets up the test environment before each test. */
    @BeforeEach
    void setUp() {
        service = new MusicStreamingService();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Configures the app with simulated user input.
     * parameter 'input' is the user input string (i.e 1) to simulate
     */
    private void setInput(String input) {

        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        app = new MusicStreamingApplication(new Scanner(inContent), service);
    }

    /** Tests playing a song with valid a input, ensuring play count increases. */
    @Test
    void testPlaySongValidInput() {
        setInput("5\n1\n" + EXIT_OPTION + "\n");
        service.initialiseDefaultSongs();
        Song songBefore = service.getLibrary().getSongs().get(0);
        long initialPlayCount = songBefore.getPlayCount();

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Playing: "));
        assertTrue(output.contains(songBefore.getTitle()));
        assertEquals(initialPlayCount + 1, songBefore.getPlayCount());
    }

    /** Tests the full process of playing a song from the menu. */
    @Test
    void testRunProcessesPlaySong() {
        setInput("5\n1\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Playing: "));
        assertTrue(output.contains("Midnight Rain"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests exiting the application. */
    @Test
    void testRunExitsOnExit() {
        setInput(EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests adding a new song with valid input. */
    @Test
    void testRunProcessesAddSong() {
        setInput("1\nTest Song\nTest Artist\n500\n2023\nPop\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Added: Test Song                    by Test Artist 500 (2023)    Pop"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests removing a song from the library. */
    @Test
    void testRunProcessesRemoveSong() {
        setInput("2\n1\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Removed: Midnight Rain"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests listing all songs in the library. */
    @Test
    void testRunProcessesListSongs() {
        setInput("3\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Music Library (10 songs)"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests filtering songs by play count. */
    @Test
    void testRunProcessesListSongsByPlayCount() {
        setInput("4\n100000000\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Songs with 100000000 or more plays:"));
        assertTrue(output.contains("Blinding Lights"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests handling an invalid menu choice. */
    @Test
    void testRunHandlesInvalidChoice() {
        setInput("x\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Invalid choice. Try again."));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }

    /** Tests toggling shuffle mode. */
    @Test
    void testToggleShuffle() {
        setInput("6\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Shuffle mode is now ON"));
    }

    /** Tests displaying playback history after playing a song. */
    @Test
    void testShowPlaybackHistory() {
        setInput("5\n1\n7\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Playing: Midnight Rain"));
        assertTrue(output.contains("Playback History"));
        assertTrue(output.contains("Midnight Rain"));
    }

    /** Tests adding a song with empty fields, requiring new attemptes. */
    @Test
    void testAddSongWithEmptyFields() {
        setInput("1\n\nValid Title\n\nValid Artist\n500\n2023\nPop\n" + EXIT_OPTION + "\n");

        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("Title cannot be empty. Try again."));
        assertTrue(output.contains("Artist cannot be empty. Try again."));
        assertTrue(output.contains("Added: Valid Title                  by Valid Artist 500 (2023)    Pop"));
        assertTrue(output.contains("Exiting Music Streaming App..."));
    }
}