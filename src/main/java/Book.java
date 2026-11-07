import java.util.Objects;

/**
 * Class representing a book in the library.
 */
public class Book {
    // Static field to count total created Book objects
    private static int bookCount = 0;

    private String title;
    private String author;
    private int year;
    private double price;
    private Genre genre; // Enum aggregation

    /**
     * Main constructor with validation.
     */
    public Book(String title, String author, int year, double price, Genre genre) {
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
        setGenre(genre);
        bookCount++; // Increment counter on creation
    }

    /**
     * Copy constructor.
     * @param other the Book object to copy from
     */
    public Book(Book other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from a null object.");
        }
        this.title = other.title;
        this.author = other.author;
        this.year = other.year;
        this.price = other.price;
        this.genre = other.genre;
        bookCount++; // Increment counter on copy creation
    }

    /**
     * Static method to get the total number of created books.
     */
    public static int getBookCount() {
        return bookCount;
    }

    public String getTitle() { return title; }
    
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty.");
        }
        this.title = title;
    }

    public String getAuthor() { return author; }
    
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        this.author = author;
    }

    public int getYear() { return year; }
    
    public void setYear(int year) {
        if (year <= 0 || year > 2026) {
            throw new IllegalArgumentException("Year must be greater than 0 and not exceed current year.");
        }
        this.year = year;
    }

    public double getPrice() { return price; }
    
    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        this.price = price;
    }

    public Genre getGenre() { return genre; }

    public void setGenre(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Genre cannot be null.");
        }
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + 
               "', year=" + year + ", price=" + price + ", genre=" + genre + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Double.compare(book.price, price) == 0 &&
                Objects.equals(title, book.title) && Objects.equals(author, book.author) &&
                genre == book.genre;
    }
}