import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library(5);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Create new object (book)");
            System.out.println("2. Display all objects in library");
            System.out.println("3. Show total books created (Static field demo)");
            System.out.println("4. Exit");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        if (library.isFull()) {
                            System.out.println("Error: Library is full.");
                            break;
                        }
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        
                        System.out.print("Enter publication year: ");
                        int year = Integer.parseInt(scanner.nextLine().trim());
                        
                        System.out.print("Enter price: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());
                        
                        System.out.println("Genres: 1-FICTION, 2-NON_FICTION, 3-SCIENCE, 4-HISTORY, 5-FANTASY");
                        System.out.print("Choose genre (1-5): ");
                        int genreChoice = Integer.parseInt(scanner.nextLine().trim());
                        Genre genre;
                        switch (genreChoice) {
                            case 1: genre = Genre.FICTION; break;
                            case 2: genre = Genre.NON_FICTION; break;
                            case 3: genre = Genre.SCIENCE; break;
                            case 4: genre = Genre.HISTORY; break;
                            case 5: genre = Genre.FANTASY; break;
                            default: throw new IllegalArgumentException("Invalid genre choice.");
                        }
                        
                        Book newBook = new Book(title, author, year, price, genre);
                        library.addBook(newBook);
                        System.out.println("Book added successfully!");
                        
                        // Copy constructor demonstration
                        System.out.print("Do you want to add a copy of this book? (y/n): ");
                        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                            if (!library.isFull()) {
                                Book copyBook = new Book(newBook);
                                library.addBook(copyBook);
                                System.out.println("Copy added successfully!");
                            } else {
                                System.out.println("Cannot add copy, library is full.");
                            }
                        }
                        break;
                        
                    case "2":
                        System.out.println("\nList of books:");
                        library.displayBooks();
                        break;
                        
                    case "3":
                        System.out.println("Total Book objects created in memory: " + Book.getBookCount());
                        break;
                        
                    case "4":
                        System.out.println("Program terminated. Goodbye!");
                        scanner.close();
                        return;
                        
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input error: a numeric value is expected.");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}