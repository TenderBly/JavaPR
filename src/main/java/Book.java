import java.util.Objects;
import java.util.UUID;

/**
 * Abstract base class representing a book in the library.
 * Implements Comparable for deterministic sorting and Identifiable for UUID support.
 */
public abstract class Book implements Comparable<Book>, Identifiable {
    private UUID uuid;
    protected String type;
    private String title;
    private String author;
    private int year;
    private double price;
    private Genre genre;

    public Book() {
        this.uuid = UUID.randomUUID();
        this.type = "Book";
    }

    public Book(String title, String author, int year, double price, Genre genre) {
        this.uuid = UUID.randomUUID();
        this.type = "Book";
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
        setGenre(genre);
    }

    public Book(Book other) {
        if (other == null) {
            throw new InvalidFieldValueException("Cannot copy from a null object.");
        }
        this.uuid = UUID.randomUUID();
        this.type = other.type;
        this.title = other.title;
        this.author = other.author;
        this.year = other.year;
        this.price = other.price;
        this.genre = other.genre;
    }

    @Override
    public UUID getUuid() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        return this.uuid;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidFieldValueException("Title cannot be empty.");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new InvalidFieldValueException("Author cannot be empty.");
        }
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year <= 0 || year > 2026) {
            throw new InvalidFieldValueException("Invalid publication year: " + year + ". Must be between 1 and 2026.");
        }
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new InvalidFieldValueException("Price must be greater than zero. Provided: " + price);
        }
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        if (genre == null) {
            throw new InvalidFieldValueException("Genre cannot be null.");
        }
        this.genre = genre;
    }

    public String toShortString() {
        return type + ": " + title + " | UUID: " + getUuid();
    }

    @Override
    public int compareTo(Book other) {
        if (other == null) {
            return 1;
        }
        int titleCompare = this.title.compareToIgnoreCase(other.title);
        if (titleCompare != 0) {
            return titleCompare;
        }
        int authorCompare = this.author.compareToIgnoreCase(other.author);
        if (authorCompare != 0) {
            return authorCompare;
        }
        int yearCompare = Integer.compare(this.year, other.year);
        if (yearCompare != 0) {
            return yearCompare;
        }
        int priceCompare = Double.compare(this.price, other.price);
        if (priceCompare != 0) {
            return priceCompare;
        }
        return this.type.compareToIgnoreCase(other.type);
    }

    @Override
    public String toString() {
        return "Book{uuid=" + getUuid() + ", type='" + type + "', title='" + title + "', author='" + author +
               "', year=" + year + ", price=" + price + ", genre=" + genre + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year
                && Double.compare(book.price, price) == 0
                && Objects.equals(type, book.type)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && genre == book.genre;
    }
}