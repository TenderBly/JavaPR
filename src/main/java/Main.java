import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> library = new ArrayList<>();

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add Regular Book");
            System.out.println("2. Add E-Book");
            System.out.println("3. Add Paper Book");
            System.out.println("4. Display all objects (Polymorphism demo)");
            System.out.println("5. Exit");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            try {
                if (choice.equals("5")) {
                    System.out.println("Program terminated. Goodbye!");
                    break;
                }
                
                if (choice.equals("4")) {
                    if (library.isEmpty()) {
                        System.out.println("Library is empty.");
                    } else {
                        System.out.println("\nLibrary Contents:");
                        for (Book b : library) {
                            // Polymorphism in action
                            System.out.println(b.toString());
                        }
                    }
                    continue;
                }

                if (!choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
                    System.out.println("Invalid choice.");
                    continue;
                }

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

                if (choice.equals("1")) {
                    library.add(new Book(title, author, year, price, genre));
                    System.out.println("Regular Book added.");
                } else if (choice.equals("2")) {
                    System.out.print("Enter file size (MB): ");
                    double size = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Enter format (e.g. PDF): ");
                    String format = scanner.nextLine();
                    library.add(new EBook(title, author, year, price, genre, size, format));
                    System.out.println("E-Book added.");
                } else if (choice.equals("3")) {
                    System.out.print("Enter page count: ");
                    int pages = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter weight (kg): ");
                    double weight = Double.parseDouble(scanner.nextLine().trim());
                    library.add(new PaperBook(title, author, year, price, genre, pages, weight));
                    System.out.println("Paper Book added.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}