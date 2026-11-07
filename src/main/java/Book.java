import java.util.Objects;

public class Book {
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
        if (other == null) throw new IllegalArgumentException("Cannot copy from a null object.");
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
        if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("Title cannot be empty.");
        this.title = title;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) throw new IllegalArgumentException("Author cannot be empty.");
        this.author = author;
    }

    public int getYear() { return year; }
    public void setYear(int year) {
        if (year <= 0 || year > 2026) throw new IllegalArgumentException("Invalid year.");
        this.year = year;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Price must be > 0.");
        this.price = price;
    }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) {
        if (genre == null) throw new IllegalArgumentException("Genre cannot be null.");
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{type='" + type + "', title='" + title + "', author='" + author + "', year=" + year + ", price=" + price + ", genre=" + genre + '}';
    }
}