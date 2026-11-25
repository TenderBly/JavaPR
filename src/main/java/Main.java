import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import com.google.gson.Gson;

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
            System.out.println("3. Modify book");
            System.out.println("4. Delete book");
            System.out.println("5. Display all objects");
            System.out.println("6. Display sorted information about all books");
            System.out.println("7. Exit and Save");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("7")) {
                FileHandler.saveLibrary(library);
                System.out.println("Program terminated. Goodbye!");
                break;
            } else if (choice.equals("1")) {
                searchMenu(scanner, library);
            } else if (choice.equals("2")) {
                createNewObjectMenu(scanner, library);
            } else if (choice.equals("3")) {
                modifyBookMenu(scanner, library);
            } else if (choice.equals("4")) {
                deleteBookMenu(scanner, library);
            } else if (choice.equals("5")) {
                library.displayAllBooks();
            } else if (choice.equals("6")) {
                sortMenu(scanner, library);
            } else {
                System.out.println("Invalid choice. Please enter 1-7.");
            }
        }
        scanner.close();
    }

    private static void sortMenu(Scanner scanner, Library library) {
        if (library.isEmpty()) {
            System.out.println("Library is empty. Nothing to sort.");
            return;
        }

        while (true) {
            System.out.println("\n--- SORT MENU ---");
            System.out.println("Choose sorting criterion:");
            System.out.println("1. Sort by title (A-Z)");
            System.out.println("2. Sort by author (A-Z)");
            System.out.println("3. Sort by price (ascending)");
            System.out.println("4. Sort by publication year (newest first)");
            System.out.println("0. Back to main menu");
            System.out.print("Select criterion: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("0")) {
                return;
            }

            if (choice.equals("1")) {
                Comparator<LibraryItem> titleComparator = (o1, o2) -> {
                    int res = o1.getBook().getTitle().compareToIgnoreCase(o2.getBook().getTitle());
                    if (res != 0) {
                        return res;
                    }
                    return o1.getBook().getAuthor().compareToIgnoreCase(o2.getBook().getAuthor());
                };
                library.displaySortedBooks(titleComparator, "Title (A-Z)");
            } else if (choice.equals("2")) {
                Comparator<LibraryItem> authorComparator = (o1, o2) -> {
                    int res = o1.getBook().getAuthor().compareToIgnoreCase(o2.getBook().getAuthor());
                    if (res != 0) {
                        return res;
                    }
                    return o1.getBook().getTitle().compareToIgnoreCase(o2.getBook().getTitle());
                };
                library.displaySortedBooks(authorComparator, "Author (A-Z)");
            } else if (choice.equals("3")) {
                Comparator<LibraryItem> priceComparator = (o1, o2) -> {
                    int res = Double.compare(o1.getBook().getPrice(), o2.getBook().getPrice());
                    if (res != 0) {
                        return res;
                    }
                    return o1.getBook().getTitle().compareToIgnoreCase(o2.getBook().getTitle());
                };
                library.displaySortedBooks(priceComparator, "Price (ascending)");
            } else if (choice.equals("4")) {
                Comparator<LibraryItem> yearComparator = (o1, o2) -> {
                    int res = Integer.compare(o2.getBook().getYear(), o1.getBook().getYear());
                    if (res != 0) {
                        return res;
                    }
                    return o1.getBook().getTitle().compareToIgnoreCase(o2.getBook().getTitle());
                };
                library.displaySortedBooks(yearComparator, "Publication Year (newest first)");
            } else {
                System.out.println("Invalid choice. Please enter 0, 1, 2, 3, or 4.");
            }
        }
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
            System.out.println("5. Search by UUID");
            System.out.println("6. Back to main menu");
            System.out.print("Select search criterion: ");

            String choice = scanner.nextLine().trim();
                    if (choice.equals("6")) { return; }
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
                        case "5":
                        System.out.print("Enter UUID: ");
                        String uuidInput = scanner.nextLine().trim();
                        try {
                            LibraryItem foundItem = library.searchByUuid(uuidInput);
                            if (foundItem == null) {
                                System.out.println("Book with UUID " + uuidInput + " not found.");
                            } else {
                                System.out.println("\nFound object:");
                                System.out.println(foundItem.toString());
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid UUID format: " + uuidInput);
                        }
                        continue;
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
        } catch (InvalidFieldValueException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Input error: numeric value expected.");
        } catch (Exception e) {
            System.out.println("Error creating object: " + e.getMessage());
        }
    }

    private static void modifyBookMenu(Scanner scanner, Library library) {
        if (library.isEmpty()) {
            System.out.println("Library is empty. Nothing to modify.");
            return;
        }

        ArrayList<LibraryItem> items = library.getItems();
        System.out.println("\n--- MODIFY BOOK ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).toString());
        }
        System.out.println("0. Back to main menu");
        System.out.print("Select book number to modify: ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            if (index == 0) {
                return;
            }
            if (index < 1 || index > items.size()) {
                System.out.println("Object not found: invalid book number.");
                return;
            }

            Book existingBook = items.get(index - 1).getBook();
            Gson gson = new Gson();
            Book updatedBook = gson.fromJson(gson.toJson(existingBook), existingBook.getClass());

            System.out.println("\nSelect attribute to modify:");
            System.out.println("1. Title");
            System.out.println("2. Author");
            System.out.println("3. Publication year");
            System.out.println("4. Price");
            System.out.println("5. Genre");
            System.out.println("0. Cancel");
            System.out.print("Choose attribute: ");

            String attrChoice = scanner.nextLine().trim();
            if (attrChoice.equals("0")) {
                return;
            }

            switch (attrChoice) {
                case "1":
                    System.out.print("Enter new title: ");
                    updatedBook.setTitle(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Enter new author: ");
                    updatedBook.setAuthor(scanner.nextLine());
                    break;
                case "3":
                    System.out.print("Enter new publication year: ");
                    updatedBook.setYear(Integer.parseInt(scanner.nextLine().trim()));
                    break;
                case "4":
                    System.out.print("Enter new price: ");
                    updatedBook.setPrice(Double.parseDouble(scanner.nextLine().trim()));
                    break;
                case "5":
                    System.out.print("Choose new genre (1-FICTION, 2-NON_FICTION, 3-SCIENCE, 4-HISTORY, 5-FANTASY): ");
                    int gChoice = Integer.parseInt(scanner.nextLine().trim());
                    if (gChoice < 1 || gChoice > Genre.values().length) {
                        System.out.println("Invalid genre number.");
                        return;
                    }
                    updatedBook.setGenre(Genre.values()[gChoice - 1]);
                    break;
                default:
                    System.out.println("Invalid attribute choice.");
                    return;
            }

            boolean result = library.update(existingBook, updatedBook);
            if (result) {
                System.out.println("Book successfully updated!");
            } else {
                System.out.println("Book was not found in the library.");
            }
        } catch (InvalidFieldValueException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (ObjectNotFoundException e) {
            System.out.println("Not found error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Input error: numeric value expected.");
        } catch (Exception e) {
            System.out.println("Modification error: " + e.getMessage());
        }
    }

    private static void deleteBookMenu(Scanner scanner, Library library) {
        if (library.isEmpty()) {
            System.out.println("Library is empty. Nothing to delete.");
            return;
        }

        ArrayList<LibraryItem> items = library.getItems();
        System.out.println("\n--- DELETE BOOK ---");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).toString());
        }
        System.out.println("0. Back to main menu");
        System.out.print("Select book number to delete: ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            if (index == 0) {
                return;
            }
            if (index < 1 || index > items.size()) {
                System.out.println("Object not found: invalid book number.");
                return;
            }

            Book targetBook = items.get(index - 1).getBook();
            System.out.print("Are you sure you want to delete '" + targetBook.getTitle() + "'? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("yes") && !confirm.equals("y")) {
                System.out.println("Deletion cancelled.");
                return;
            }

            boolean deleted = library.delete(targetBook);
            if (deleted) {
                System.out.println("Book successfully deleted!");
            } else {
                System.out.println("Book was not found in the library.");
            }
        } catch (ObjectNotFoundException e) {
            System.out.println("Not found error: " + e.getMessage());
        } catch (InvalidFieldValueException e) {
            System.out.println("Validation error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Input error: numeric value expected.");
        } catch (Exception e) {
            System.out.println("Deletion error: " + e.getMessage());
        }
    }
}