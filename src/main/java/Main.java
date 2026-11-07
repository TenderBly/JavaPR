import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Створення масиву рівно на 5 об'єктів згідно з Варіантом 2
        Book[] library = new Book[5]; 
        
        System.out.println("Будь ласка, введіть дані для 5 книг.");
        
        // Заповнення масиву даними з клавіатури
        for (int i = 0; i < library.length; i++) {
            System.out.print("Введіть назву книги " + (i + 1) + ": ");
            String title = scanner.nextLine();
            
            System.out.print("Введіть автора книги " + (i + 1) + ": ");
            String author = scanner.nextLine();
            
            library[i] = new Book(title, author);
        }
        
        System.out.println("\nІнформація про всі створені об'єкти:");
        // Виведення інформації через метод toString()
        for (Book book : library) {
            System.out.println(book.toString());
        }
        
        scanner.close();
    }
}