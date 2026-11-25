/**
 * Wrapper entry storing a Book object and its quantity in the library.
 */
public class LibraryItem implements Comparable<LibraryItem> {
    private Book book;
    private Integer quantity;

    public LibraryItem(Book book, Integer quantity) {
        if (book == null) {
            throw new InvalidFieldValueException("Book cannot be null.");
        }
        if (quantity == null || quantity <= 0) {
            throw new InvalidFieldValueException("Quantity must be greater than zero.");
        }
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        if (book == null) {
            throw new InvalidFieldValueException("Book cannot be null.");
        }
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        if (amount <= 0) {
            throw new InvalidFieldValueException("Quantity to add must be positive.");
        }
        this.quantity = Integer.valueOf(this.quantity.intValue() + amount);
    }

    @Override
    public int compareTo(LibraryItem other) {
        if (other == null) {
            return 1;
        }
        return this.book.compareTo(other.book);
    }

    @Override
    public String toString() {
        return book.toString() + " | Quantity: " + quantity;
    }
}