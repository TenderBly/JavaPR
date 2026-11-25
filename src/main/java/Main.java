import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library("SumDU Central Library", "2 Rymskogo-Korsakova St");

        // Load data from input.txt using Library.addNewBook()
        FileHandler.loadLibrary(library);

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Search object");
            System.out.println("2. Add new book (with quantity)");
            System.out.println("3. Display all objects");
            System.out.println("4. Display sorted information about all books");
            System.out.println("5. Exit and Save");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("5")) {
                FileHandler.saveLibrary(library);
                System.out.println("Program terminated. Goodbye!");
                break;
            } else if (choice.equals("1")) {
                searchMenu(scanner, library);
            } else if (choice.equals("2")) {
                createNewObjectMenu(scanner, library);
            } else if (choice.equals("3")) {
                library.displayAllBooks();
            } else if (choice.equals("4")) {
                library.displaySortedBooks();
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 5.");
            }
        }
        scanner.close();
    }

    private static void searchMenu(Scanner scanner, Library library) {
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
                ArrayList<LibraryItem> results = null;
                switch (choice) {
                    case "1":
                        System.out.print("Enter title (or part of title): ");
                        results = library.searchByTitle(scanner.nextLine().trim());
                        break;
                    case "2":
                        System.out.print("Enter author (or part of name): ");
                        results = library.searchByAuthor(scanner.nextLine().trim());
                        break;
                    case "3":
                        System.out.print("Choose genre (1-FICTION, 2-NON_FICTION, 3-SCIENCE, 4-HISTORY, 5-FANTASY): ");
                        int gChoice = Integer.parseInt(scanner.nextLine().trim());
                        if (gChoice < 1 || gChoice > Genre.values().length) {
                            System.out.println("Invalid genre number.");
                            continue;
                        }
                        results = library.searchByGenre(Genre.values()[gChoice - 1]);
                        break;
                    case "4":
                        System.out.print("Enter maximum price: ");
                        double maxPrice = Double.parseDouble(scanner.nextLine().trim());
                        if (maxPrice <= 0) {
                            System.out.println("Price must be greater than zero.");
                            continue;
                        }
                        results = library.searchByMaxPrice(maxPrice);
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

    private static void displaySearchResults(ArrayList<LibraryItem> results) {
        if (results == null || results.isEmpty()) {
            System.out.println("No objects found matching the search criteria.");
            return;
        }
        System.out.println("\nFound objects (" + results.size() + "):");
        for (LibraryItem item : results) {
            System.out.println(item.toString());
        }
    }

    private static void createNewObjectMenu(Scanner scanner, Library library) {
        System.out.println("\n--- ADD NEW BOOK ---");
        System.out.println("1. E-Book");
        System.out.println("2. Paper Book");
        System.out.println("3. Audio Book");
        System.out.println("4. Interactive E-Book");
        System.out.println("5. Back to main menu");
        System.out.print("Select type: ");

        String typeChoice = scanner.nextLine().trim();
        if (typeChoice.equals("5")) return;

        if (!typeChoice.equals("1") && !typeChoice.equals("2") && !typeChoice.equals("3") && !typeChoice.equals("4")) {
            System.out.println("Invalid type selected.");
            return;
        }

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
            if (gChoice < 1 || gChoice > Genre.values().length) {
                System.out.println("Invalid genre number.");
                return;
            }
            Genre genre = Genre.values()[gChoice - 1];

            Book newBook = null;
            switch (typeChoice) {
                case "1":
                    System.out.print("Enter file size (MB): ");
                    double size = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter format: ");
                    String format = scanner.nextLine();
                    newBook = new EBook(title, author, year, price, genre, size, format);
                    break;
                case "2":
                    System.out.print("Enter page count: ");
                    int pages = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter weight (kg): ");
                    double weight = Double.parseDouble(scanner.nextLine().trim());
                    newBook = new PaperBook(title, author, year, price, genre, pages, weight);
                    break;
                case "3":
                    System.out.print("Enter narrator name: ");
                    String narrator = scanner.nextLine();
                    System.out.print("Enter duration (minutes): ");
                    int duration = Integer.parseInt(scanner.nextLine().trim());
                    newBook = new AudioBook(title, author, year, price, genre, narrator, duration);
                    break;
                case "4":
                    System.out.print("Enter file size (MB): ");
                    double iSize = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter format: ");
                    String iFormat = scanner.nextLine();
                    System.out.print("Has video content? (true/false): ");
                    boolean hasVideo = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Has quizzes? (true/false): ");
                    boolean hasQuizzes = Boolean.parseBoolean(scanner.nextLine().trim());
                    newBook = new InteractiveEBook(title, author, year, price, genre, iSize, iFormat, hasVideo, hasQuizzes);
                    break;
            }

            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            library.addNewBook(newBook, quantity);
            System.out.println("Book added/updated successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Input error: numeric value expected.");
        } catch (Exception e) {
            System.out.println("Error creating object: " + e.getMessage());
        }
    }
}