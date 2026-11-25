import java.util.ArrayList;

/**
 * Container class representing a library that aggregates Book items and their quantities.
 */
public class Library {
    private String name;
    private String address;
    private ArrayList<LibraryItem> items;

    public Library(String name, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Library name cannot be empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Library address cannot be empty.");
        }
        this.name = name;
        this.address = address;
        this.items = new ArrayList<LibraryItem>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<LibraryItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Adds a book to the library or increases its quantity if it already exists.
     */
    public void addNewBook(Book bk, int quantity) {
        if (bk == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        for (LibraryItem item : items) {
            if (item.getBook().equals(bk)) {
                item.addQuantity(quantity);
                return;
            }
        }
        items.add(new LibraryItem(bk, Integer.valueOf(quantity)));
    }

    public ArrayList<LibraryItem> searchByTitle(String titleQuery) {
        ArrayList<LibraryItem> found = new ArrayList<LibraryItem>();
        if (titleQuery == null || titleQuery.isEmpty()) {
            return found;
        }
        String lowerQuery = titleQuery.toLowerCase();
        for (LibraryItem item : items) {
            if (item.getBook().getTitle().toLowerCase().contains(lowerQuery)) {
                found.add(item);
            }
        }
        return found;
    }

    public ArrayList<LibraryItem> searchByAuthor(String authorQuery) {
        ArrayList<LibraryItem> found = new ArrayList<LibraryItem>();
        if (authorQuery == null || authorQuery.isEmpty()) {
            return found;
        }
        String lowerQuery = authorQuery.toLowerCase();
        for (LibraryItem item : items) {
            if (item.getBook().getAuthor().toLowerCase().contains(lowerQuery)) {
                found.add(item);
            }
        }
        return found;
    }

    public ArrayList<LibraryItem> searchByGenre(Genre genre) {
        ArrayList<LibraryItem> found = new ArrayList<LibraryItem>();
        if (genre == null) {
            return found;
        }
        for (LibraryItem item : items) {
            if (item.getBook().getGenre() == genre) {
                found.add(item);
            }
        }
        return found;
    }

    public ArrayList<LibraryItem> searchByMaxPrice(double maxPrice) {
        ArrayList<LibraryItem> found = new ArrayList<LibraryItem>();
        for (LibraryItem item : items) {
            if (item.getBook().getPrice() <= maxPrice) {
                found.add(item);
            }
        }
        return found;
    }

    public void displayAllBooks() {
        if (items.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }
        System.out.println("\n" + name + " (" + address + ") - Contents:");
        for (LibraryItem item : items) {
            System.out.println(item.toString());
        }
    }
}