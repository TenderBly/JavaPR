import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load data from file at startup
        ArrayList<Book> library = FileHandler.loadFromJson();

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Search object");
            System.out.println("2. Create new object (Book)");
            System.out.println("3. Display all objects");
            System.out.println("4. Exit and Save");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("4")) {
                FileHandler.saveToJson(library);
                System.out.println("Program terminated. Goodbye!");
                break;
            } else if (choice.equals("1")) {
                searchMenu(scanner, library);
            } else if (choice.equals("2")) {
                createNewObjectMenu(scanner, library);
            } else if (choice.equals("3")) {
                displayAllBooks(library);
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
        scanner.close();
    }

    private static void displayAllBooks(ArrayList<Book> library) {
        if (library.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }
        System.out.println("\nLibrary Contents:");
        for (Book b : library) {
            System.out.println(b.toString());
        }
    }

    private static void searchMenu(Scanner scanner, ArrayList<Book> library) {
        if (library.isEmpty()) {
            System.out.println("Library is empty. Nothing to search.");
            return;
        }

        while (true) {
            System.out.println("\n--- SEARCH MENU ---");
            System.out.println("1. Search by title");
            System.out.println("2. Search by author");
            System.out.println("3. Search by genre");
            System.out.println("4. Search by maximum price");
            System.out.println("5. Back to main menu");
            System.out.print("Select search criterion: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("5")) {
                return;
            }

            try {
                ArrayList<Book> results = null;

                switch (choice) {
                    case "1":
                        System.out.print("Enter title (or part of title): ");
                        String titleQuery = scanner.nextLine().trim();
                        results = searchByTitle(library, titleQuery);
                        break;
                    case "2":
                        System.out.print("Enter author (or part of name): ");
                        String authorQuery = scanner.nextLine().trim();
                        results = searchByAuthor(library, authorQuery);
                        break;
                    case "3":
                        System.out.print("Choose genre (1-FICTION, 2-NON_FICTION, 3-SCIENCE, 4-HISTORY, 5-FANTASY): ");
                        int gChoice = Integer.parseInt(scanner.nextLine().trim());
                        if (gChoice < 1 || gChoice > Genre.values().length) {
                            System.out.println("Invalid genre number.");
                            continue;
                        }
                        Genre genre = Genre.values()[gChoice - 1];
                        results = searchByGenre(library, genre);
                        break;
                    case "4":
                        System.out.print("Enter maximum price: ");
                        double maxPrice = Double.parseDouble(scanner.nextLine().trim());
                        if (maxPrice <= 0) {
                            System.out.println("Price must be greater than zero.");
                            continue;
                        }
                        results = searchByMaxPrice(library, maxPrice);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1-5.");
                        continue;
                }

                displaySearchResults(results);
            } catch (NumberFormatException e) {
                System.out.println("Input error: numeric value expected.");
            } catch (Exception e) {
                System.out.println("Search error: " + e.getMessage());
            }
        }
    }

    public static ArrayList<Book> searchByTitle(ArrayList<Book> library, String titleQuery) {
        ArrayList<Book> found = new ArrayList<Book>();
        if (titleQuery == null || titleQuery.isEmpty()) {
            return found;
        }
        String lowerQuery = titleQuery.toLowerCase();
        for (Book b : library) {
            if (b.getTitle().toLowerCase().contains(lowerQuery)) {
                found.add(b);
            }
        }
        return found;
    }

    public static ArrayList<Book> searchByAuthor(ArrayList<Book> library, String authorQuery) {
        ArrayList<Book> found = new ArrayList<Book>();
        if (authorQuery == null || authorQuery.isEmpty()) {
            return found;
        }
        String lowerQuery = authorQuery.toLowerCase();
        for (Book b : library) {
            if (b.getAuthor().toLowerCase().contains(lowerQuery)) {
                found.add(b);
            }
        }
        return found;
    }

    public static ArrayList<Book> searchByGenre(ArrayList<Book> library, Genre genre) {
        ArrayList<Book> found = new ArrayList<Book>();
        if (genre == null) {
            return found;
        }
        for (Book b : library) {
            if (b.getGenre() == genre) {
                found.add(b);
            }
        }
        return found;
    }

    public static ArrayList<Book> searchByMaxPrice(ArrayList<Book> library, double maxPrice) {
        ArrayList<Book> found = new ArrayList<Book>();
        for (Book b : library) {
            if (b.getPrice() <= maxPrice) {
                found.add(b);
            }
        }
        return found;
    }

    private static void displaySearchResults(ArrayList<Book> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No objects found matching the search criteria.");
            return;
        }
        System.out.println("\nFound objects (" + results.size() + "):");
        for (Book b : results) {
            System.out.println(b.toString());
        }
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