import java.time.Year;

/**
 * Represents a song with title, artist, play count, year, and genre.
 */
public class Song {
    private final String title; // Song title
    private final String artist; // Artist name
    private long playCount; // Number of times played
    private final int year; // Release year
    private final String genre; // Music genre

    /**
     * Constructs a song with the specified attributes.
     * @param title Song title
     * @param artist Artist name
     * @param playCount Initial play count
     * @param year Release year
     * @param genre Music genre
     * @throws IllegalArgumentException if title or artist is null/empty, or year is in the future
     */
    public Song(String title, String artist, long playCount, int year, String genre) throws IllegalArgumentException {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (year > Year.now().getValue()) {
            throw new IllegalArgumentException("Year cannot be greater than " + Year.now().getValue());
        }
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist cannot be null or empty");
        }
        this.title = title;
        this.artist = artist;
        this.playCount = playCount;
        this.year = year;
        this.genre = genre;
    }

    /** return The song title */
    public String getTitle() {
        return title;
    }

    /** return The artist name */
    public String getArtist() {
        return artist;
    }

    /** return The current play count */
    public long getPlayCount() {
        return playCount;
    }

    /** return The release year */
    public int getYear() {
        return year;
    }

    /** return The music genre */
    public String getGenre() {
        return genre;
    }

    /**
     * Returns a formatted string representation of the song.
     * returns the song details in a fixed-width format
     */
    @Override
    public String toString() {
        return String.format("%-28s %-14s %d (%d)    %s",
                title, "by " + artist, playCount, year, genre);
    }

    /** Increments the play count by 1. */
    public void incrementPlayCount() {
        playCount++;
    }
}