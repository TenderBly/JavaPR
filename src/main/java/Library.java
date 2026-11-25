import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

/**
 * Container class managing a collection of LibraryItem objects.
 */
public class Library {
    private String name;
    private String address;
    private ArrayList<LibraryItem> items;

    public Library(String name, String address) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidFieldValueException("Library name cannot be empty.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidFieldValueException("Library address cannot be empty.");
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

    public void addNewBook(Book bk, int quantity) {
        if (bk == null) {
            throw new InvalidFieldValueException("Book cannot be null.");
        }
        if (quantity <= 0) {
            throw new InvalidFieldValueException("Quantity must be greater than zero. Provided: " + quantity);
        }

        for (LibraryItem item : items) {
            if (item.getBook().equals(bk)) {
                item.addQuantity(quantity);
                return;
            }
        }
        items.add(new LibraryItem(bk, Integer.valueOf(quantity)));
    }

    /**
     * Finds existingObject in the library and replaces it with newObject.
     * Throws ObjectNotFoundException if existingObject is not found.
     */
    public boolean update(Book existingObject, Book newObject) {
        if (existingObject == null || newObject == null) {
            throw new InvalidFieldValueException("Existing and new book objects cannot be null.");
        }
        for (LibraryItem item : items) {
            if (item.getBook().equals(existingObject)) {
                item.setBook(newObject);
                return true;
            }
        }
        throw new ObjectNotFoundException("Cannot update: book '" + existingObject.getTitle() + "' was not found in the library.");
    }

    /**
     * Finds existingObject in the library and removes it from the collection.
     * Throws ObjectNotFoundException if existingObject is not found.
     */
    public boolean delete(Book existingObject) {
        if (existingObject == null) {
            throw new InvalidFieldValueException("Book to delete cannot be null.");
        }
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getBook().equals(existingObject)) {
                items.remove(i);
                return true;
            }
        }
        throw new ObjectNotFoundException("Cannot delete: book '" + existingObject.getTitle() + "' was not found in the library.");
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

    public ArrayList<LibraryItem> searchByTitle(String title) {
        ArrayList<LibraryItem> results = new ArrayList<LibraryItem>();
        if (title == null || title.trim().isEmpty()) {
            return results;
        }
        String query = title.trim().toLowerCase();
        for (LibraryItem item : items) {
            if (item.getBook().getTitle().toLowerCase().contains(query)) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<LibraryItem> searchByAuthor(String author) {
        ArrayList<LibraryItem> results = new ArrayList<LibraryItem>();
        if (author == null || author.trim().isEmpty()) {
            return results;
        }
        String query = author.trim().toLowerCase();
        for (LibraryItem item : items) {
            if (item.getBook().getAuthor().toLowerCase().contains(query)) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<LibraryItem> searchByGenre(Genre genre) {
        ArrayList<LibraryItem> results = new ArrayList<LibraryItem>();
        if (genre == null) {
            return results;
        }
        for (LibraryItem item : items) {
            if (item.getBook().getGenre() == genre) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<LibraryItem> searchByMaxPrice(double maxPrice) {
        ArrayList<LibraryItem> results = new ArrayList<LibraryItem>();
        for (LibraryItem item : items) {
            if (item.getBook().getPrice() <= maxPrice) {
                results.add(item);
            }
        }
        return results;
    }

    public LibraryItem searchByUuid(String uuidText) {
        if (uuidText == null || uuidText.trim().isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be empty.");
        }
        UUID targetUuid = UUID.fromString(uuidText.trim());
        for (LibraryItem item : items) {
            if (item.getBook().getUuid().equals(targetUuid)) {
                return item;
            }
        }
        return null;
    }

    public ArrayList<LibraryItem> getSortedItems() {
        ArrayList<LibraryItem> sorted = new ArrayList<LibraryItem>(items);
        Collections.sort(sorted);
        return sorted;
    }

    public void displaySortedBooks() {
        if (items.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }
        ArrayList<LibraryItem> sorted = getSortedItems();
        System.out.println("\n" + name + " (" + address + ") - Sorted Contents:");
        for (LibraryItem item : sorted) {
            System.out.println(item.toString());
        }
    }

    public ArrayList<LibraryItem> getSortedItems(Comparator<LibraryItem> comparator) {
        ArrayList<LibraryItem> sorted = new ArrayList<LibraryItem>(items);
        if (comparator != null) {
            Collections.sort(sorted, comparator);
        } else {
            Collections.sort(sorted);
        }
        return sorted;
    }

    public void displaySortedBooks(Comparator<LibraryItem> comparator, String criterionName) {
        if (items.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }
        ArrayList<LibraryItem> sorted = getSortedItems(comparator);
        System.out.println("\n" + name + " (" + address + ") - Sorted by " + criterionName + ":");
        for (LibraryItem item : sorted) {
            System.out.println(item.toString());
        }
    }
}