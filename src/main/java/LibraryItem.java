/**
 * Wrapper entry storing a Book object and its quantity in the library.
 */
public class LibraryItem {
    private Book book;
    private Integer quantity;

    public LibraryItem(Book book, Integer quantity) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void addQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantity to add must be positive.");
        }
        this.quantity = Integer.valueOf(this.quantity.intValue() + amount);
    }

    @Override
    public String toString() {
        return book.toString() + " | Quantity: " + quantity;
    }
}