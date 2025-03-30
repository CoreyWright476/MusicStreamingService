import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Playlist {

    private final String name;
    private final List<Song> songs;
    private boolean shuffle;

    public Playlist(String name) {

        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Playlist name cannot be null or empty");
        }
        this.name = name;
        this.songs = new ArrayList<Song>();
        this.shuffle = false;
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs(boolean shuffle) {
        if(shuffle) {
            List<Song> shuffled = new ArrayList<>(songs);
            Collections.shuffle(shuffled);
            return shuffled;
        }

        return new ArrayList<>(songs);
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public int size() {
        return songs.size();
    }

    @Override
    public String toString() {
        return name + " (" + songs.size() + " songs" + (shuffle ? ", shuffled" : "") + ")";
    }

}
