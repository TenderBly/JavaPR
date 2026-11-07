import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> library = new ArrayList<>();

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Create new object (Book)");
            System.out.println("2. Display all objects");
            System.out.println("3. Exit");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("3")) {
                System.out.println("Program terminated. Goodbye!");
                break;
            }

            if (choice.equals("2")) {
                if (library.isEmpty()) {
                    System.out.println("Library is empty.");
                } else {
                    System.out.println("\nLibrary Contents:");
                    for (Book b : library) {
                        System.out.println(b.toString());
                    }
                }
                continue;
            }

            if (choice.equals("1")) {
                createNewObjectMenu(scanner, library);
            } else {
                System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void createNewObjectMenu(Scanner scanner, ArrayList<Book> library) {
        System.out.println("\n--- CREATE NEW OBJECT ---");
        System.out.println("1. Regular Book");
        System.out.println("2. E-Book");
        System.out.println("3. Paper Book");
        System.out.println("4. Audio Book");
        System.out.println("5. Interactive E-Book");
        System.out.println("6. Back to main menu");
        System.out.print("Select type: ");

        String typeChoice = scanner.nextLine().trim();
        if (typeChoice.equals("6")) return;

        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter author: ");
            String author = scanner.nextLine();
            System.out.print("Enter publication year: ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Enter price: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Choose genre (1-FICTION, 2-NON_FICTION, 3-SCIENCE, 4-HISTORY, 5-FANTASY): ");
            int gChoice = Integer.parseInt(scanner.nextLine().trim());
            Genre genre = Genre.values()[gChoice - 1];

            switch (typeChoice) {
                case "1":
                    library.add(new Book(title, author, year, price, genre));
                    System.out.println("Regular Book added.");
                    break;
                case "2":
                    System.out.print("Enter file size (MB): ");
                    double size = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter format: ");
                    String format = scanner.nextLine();
                    library.add(new EBook(title, author, year, price, genre, size, format));
                    System.out.println("E-Book added.");
                    break;
                case "3":
                    System.out.print("Enter page count: ");
                    int pages = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter weight (kg): ");
                    double weight = Double.parseDouble(scanner.nextLine().trim());
                    library.add(new PaperBook(title, author, year, price, genre, pages, weight));
                    System.out.println("Paper Book added.");
                    break;
                case "4":
                    System.out.print("Enter narrator name: ");
                    String narrator = scanner.nextLine();
                    System.out.print("Enter duration (minutes): ");
                    int duration = Integer.parseInt(scanner.nextLine().trim());
                    library.add(new AudioBook(title, author, year, price, genre, narrator, duration));
                    System.out.println("Audio Book added.");
                    break;
                case "5":
                    System.out.print("Enter file size (MB): ");
                    double iSize = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter format: ");
                    String iFormat = scanner.nextLine();
                    System.out.print("Has video content? (true/false): ");
                    boolean hasVideo = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Has quizzes? (true/false): ");
                    boolean hasQuizzes = Boolean.parseBoolean(scanner.nextLine().trim());
                    library.add(new InteractiveEBook(title, author, year, price, genre, iSize, iFormat, hasVideo, hasQuizzes));
                    System.out.println("Interactive E-Book added.");
                    break;
                default:
                    System.out.println("Invalid type selected.");
            }
        } catch (Exception e) {
            System.out.println("Error creating object: " + e.getMessage());
        }
    }
}