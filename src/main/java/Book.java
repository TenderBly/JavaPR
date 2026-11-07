import java.util.Objects;

/**
 * Клас, що описує книгу в бібліотеці.
 */
public class Book {
    private String title;
    private String author;
    private int year;
    private double price;

    /**
     * Конструктор з перевіркою параметрів.
     * @param title назва книги
     * @param author автор книги
     * @param year рік видання
     * @param price ціна книги
     */
    public Book(String title, String author, int year, double price) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
    }

    public String getTitle() { return title; }
    
    /**
     * Встановлює назву книги.
     * @param title назва
     * @throws IllegalArgumentException якщо рядок порожній або null
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва книги не може бути порожньою.");
        }
        this.title = title;
    }

    public String getAuthor() { return author; }
    
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Автор не може бути порожнім.");
        }
        this.author = author;
    }

    public int getYear() { return year; }
    
    public void setYear(int year) {
        if (year <= 0 || year > 2026) {
            throw new IllegalArgumentException("Рік видання має бути більше 0 і не перевищувати поточний рік.");
        }
        this.year = year;
    }

    public double getPrice() { return price; }
    
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна має бути більше нуля.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" + "title='" + title + '\'' + ", author='" + author + '\'' +
                ", year=" + year + ", price=" + price + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Double.compare(book.price, price) == 0 &&
                Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }
}