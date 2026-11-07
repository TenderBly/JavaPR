/**
 * Class representing a library that aggregates Book objects.
 */
public class Library {
    private Book[] books;
    private int count;

    public Library(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        books = new Book[capacity];
        count = 0;
    }

    public void addBook(Book book) {
        if (isFull()) {
            throw new IllegalStateException("Library is full.");
        }
        if (book == null) {
            throw new IllegalArgumentException("Cannot add a null book.");
        }
        books[count] = book;
        count++;
    }

    public void displayBooks() {
        if (count == 0) {
            System.out.println("Library is empty.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(books[i].toString());
        }
    }

    public boolean isFull() {
        return count >= books.length;
    }
}