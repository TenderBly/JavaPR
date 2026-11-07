import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Book[] library = new Book[5];
        int count = 0;

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Create new object (book)");
            System.out.println("2. Display information about all objects");
            System.out.println("3. Exit");
            System.out.print("Choose action: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        if (count >= library.length) {
                            System.out.println("Error: Library is full (maximum 5 books).");
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
                        
                        library[count] = new Book(title, author, year, price);
                        count++;
                        System.out.println("Book added successfully!");
                        break;

                    case "2":
                        if (count == 0) {
                            System.out.println("Library is currently empty.");
                        } else {
                            System.out.println("\nList of books:");
                            for (int i = 0; i < count; i++) {
                                System.out.println(library[i].toString());
                            }
                        }
                        break;

                    case "3":
                        System.out.println("Program terminated. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input error: a numeric value is expected for year and price.");
            } catch (IllegalArgumentException e) {
                System.out.println("Validation error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }
}