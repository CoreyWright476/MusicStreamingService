import java.util.Scanner;

public class MusicStreamingApplication {
    private final Scanner scanner;
    private final MusicStreamingService service;

    public MusicStreamingApplication(Scanner scanner, MusicStreamingService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public static void main(String[] args) {
        MusicStreamingApplication app = new MusicStreamingApplication(
                new Scanner(System.in), new MusicStreamingService()
        );
        app.run();
    }

    public void run() {
        service.initialiseDefaultSongs();
        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();
            if (choice.equals("6")) { // Updated to 6 since 5 is exit
                System.out.println("Exiting Music Streaming App...");
                break;
            }
            processChoice(choice);
        }
        scanner.close();
    }

    private void processChoice(String choice) {
        switch (choice) {
            case "1":
                addNewSong();
                break;
            case "2":
                removeSong();
                break;
            case "3":
                listSongs();
                break;
            case "4":
                listSongsByPlayCount();
                break;
            case "5":
                playSong();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    private void displayMenu() {
        System.out.println("\nMusic Streaming App Menu:");
        System.out.println("1. Add a new song");
        System.out.println("2. Remove a song");
        System.out.println("3. List all songs");
        System.out.println("4. List songs over specific play count");
        System.out.println("5. Play a song");
        System.out.println("6. Exit");
        System.out.print("Choice: ");
    }

    private void listSongs() {
        System.out.println("\n" + service.getLibrary());
        int index = 1;
        for (Song song : service.getLibrary().getSongs(false)) {
            System.out.println(index++ + ". " + song);
        }
    }

    void addNewSong() {
        System.out.print("Enter song title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter artist: ");
        String artist = scanner.nextLine().trim();
        System.out.print("Enter play count: ");
        long playCount;
        try {
            playCount = Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid play count. Using 0.");
            playCount = 0;
        }
        System.out.print("Enter year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Using current year.");
            year = java.time.Year.now().getValue();
        }
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine().trim();

        Song song = new Song(title, artist, playCount, year, genre);
        service.addSong(song);
        System.out.println("Added: " + song);
    }

    void removeSong() {
        listSongs();
        System.out.print("Enter song number to remove: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            Song song = service.getLibrary().getSongs(false).get(index);
            service.removeSong(song);
            System.out.println("Removed: " + song);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Invalid selection: " + e.getMessage());
        }
    }

    void listSongsByPlayCount() {
        System.out.print("Enter minimum play count: ");
        try {
            long minPlays = Long.parseLong(scanner.nextLine().trim());
            System.out.println("\nSongs with " + minPlays + " or more plays:");
            int index = 1;
            for (Song song : service.filterSongsByPlays(minPlays)) {
                System.out.println(index++ + ". " + song);
            }
            if (index == 1) {
                System.out.println("No songs found with " + minPlays + " or more plays.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid play count: " + e.getMessage());
        }
    }

    void playSong() {
        listSongs();
        System.out.print("Enter song number to play: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            service.playSong(index);
            System.out.println("Playing: " + service.getLibrary().getSongs(false).get(index));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Invalid selection: " + e.getMessage());
        }
    }
}