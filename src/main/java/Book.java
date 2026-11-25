import java.util.Objects;

/**
 * Abstract base class representing a book in the library.
 * Implements Comparable for deterministic sorting by title, author, year, and price.
 */
public abstract class Book implements Comparable<Book> {
    protected String type;
    private String title;
    private String author;
    private int year;
    private double price;
    private Genre genre;

    public Book() {
        this.type = "Book";
    }

    public Book(String title, String author, int year, double price, Genre genre) {
        this.type = "Book";
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setPrice(price);
        setGenre(genre);
    }

    public Book(Book other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot copy from a null object.");
        }
        this.type = other.type;
        this.title = other.title;
        this.author = other.author;
        this.year = other.year;
        this.price = other.price;
        this.genre = other.genre;
    }

    public String getType() { return type; }
    public String getTitle() { return title; }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
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
            throw new IllegalArgumentException("Invalid year.");
        }
        this.year = year;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be > 0.");
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
        return "Book{type='" + type + "', title='" + title + "', author='" + author +
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