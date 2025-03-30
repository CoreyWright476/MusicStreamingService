import java.time.Year;

public class Song {

    private final String title;
    private final String artist;
    private long playCount;
    private final int year;
    private final String genre;

    public Song(String title, String artist, long playCount, int year, String genre) throws IllegalArgumentException {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        if(year > Year.now().getValue()) {
            throw new IllegalArgumentException("Year cannot be greater than 2025");
        }

        if(artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Artist cannot be null or empty");
        }

        this.title = title;
        this.artist = artist;
        this.playCount = playCount;
        this.year = year;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getPlayCount() {
        return playCount;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return String.format("%-28s %-14s %d (%d)    %s",
                title, "by " + artist, playCount, year, genre);
    }

    public void incrementPlayCount() {
        playCount++;
    }

}
