import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Book[] library = new Book[5];
        int count = 0;

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Створити новий об'єкт (книгу)");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        if (count >= library.length) {
                            System.out.println("Помилка: Бібліотека заповнена (максимум 5 книг).");
                            break;
                        }
                        System.out.print("Введіть назву: ");
                        String title = scanner.nextLine();
                        
                        System.out.print("Введіть автора: ");
                        String author = scanner.nextLine();
                        
                        System.out.print("Введіть рік видання: ");
                        int year = Integer.parseInt(scanner.nextLine().trim());
                        
                        System.out.print("Введіть ціну: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());
                        
                        library[count] = new Book(title, author, year, price);
                        count++;
                        System.out.println("Книгу успішно додано!");
                        break;

                    case "2":
                        if (count == 0) {
                            System.out.println("Бібліотека наразі порожня.");
                        } else {
                            System.out.println("\nСписок книг:");
                            for (int i = 0; i < count; i++) {
                                System.out.println(library[i].toString());
                            }
                        }
                        break;

                    case "3":
                        System.out.println("Робота завершена. До побачення!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Некоректний вибір. Введіть 1, 2 або 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка вводу: для року та ціни очікується числове значення.");
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка валідації: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Сталася непередбачена помилка: " + e.getMessage());
            }
        }
    }
}