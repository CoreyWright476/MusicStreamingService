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
            if (choice.equals("q")) {
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
                listSongs();
                break;
            case "2":
                playSong();
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    void listSongs() {
        System.out.println("\n" + service.getLibrary());
        int index = 1;
        for (Song song : service.getLibrary().getSongs(false)) {
            System.out.println(index++ + ". " + song);
        }
    }

    void displayMenu() {
        System.out.println("\n=== Music Streaming App ===");
        System.out.println("1. List Songs");
        System.out.println("2. Play Song");
        System.out.println("q. Quit");
        System.out.print("Choice: ");
    }

    void playSong() {
        listSongs();
        System.out.print("Enter song number to play: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            service.playSong(index);
            System.out.println("Playing: " + service.getLibrary().getSongs(false).get(index));
        } catch (Exception e) {
            System.out.println("Invalid selection: " + e.getMessage());
        }
    }
}