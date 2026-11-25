import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldHandleEmptyLibrarySorting() {
        Library library = new Library("Test Library", "Main St");
        ArrayList<LibraryItem> sorted = library.getSortedItems();
        assertTrue(sorted.isEmpty());
    }

    @Test
    void shouldHandleSingleElementSorting() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);

        ArrayList<LibraryItem> sorted = library.getSortedItems();
        assertEquals(1, sorted.size());
        assertEquals("Kobzar", sorted.get(0).getBook().getTitle());
    }

    @Test
    void shouldSortMultipleBooksByTitleUsingComparable() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new AudioBook("Atomic Habits", "James Clear", 2018, 320.0, Genre.NON_FICTION, "James Clear", 335), 2);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);

        ArrayList<LibraryItem> sorted = library.getSortedItems();
        assertEquals(3, sorted.size());
        assertEquals("Atomic Habits", sorted.get(0).getBook().getTitle());
        assertEquals("Dune", sorted.get(1).getBook().getTitle());
        assertEquals("Kobzar", sorted.get(2).getBook().getTitle());
    }

    @Test
    void shouldIncreaseQuantityWhenAddingExistingBook() {
        Library library = new Library("Test Library", "Main St");
        PaperBook b1 = new PaperBook("Clean Code", "Robert Martin", 2008, 450.0, Genre.SCIENCE, 464, 0.7);
        PaperBook b2 = new PaperBook("Clean Code", "Robert Martin", 2008, 450.0, Genre.SCIENCE, 464, 0.7);

        library.addNewBook(b1, 3);
        library.addNewBook(b2, 2);

        assertEquals(1, library.getItems().size());
        assertEquals(Integer.valueOf(5), library.getItems().get(0).getQuantity());
    }
}