import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldHandleEmptyLibrarySorting() {
        Library library = new Library("Test Library", "Main St");
        ArrayList<LibraryItem> sorted = library.getSortedItems();
        assertTrue(sorted.isEmpty());
    }

    @Test
    void shouldSortByAuthorUsingAnonymousComparator() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);
        library.addNewBook(new AudioBook("Atomic Habits", "James Clear", 2018, 320.0, Genre.NON_FICTION, "James Clear", 335), 2);

        Comparator<LibraryItem> authorCmp = new Comparator<LibraryItem>() {
            @Override
            public int compare(LibraryItem o1, LibraryItem o2) {
                return o1.getBook().getAuthor().compareToIgnoreCase(o2.getBook().getAuthor());
            }
        };

        ArrayList<LibraryItem> sorted = library.getSortedItems(authorCmp);
        assertEquals("Frank Herbert", sorted.get(0).getBook().getAuthor());
        assertEquals("James Clear", sorted.get(1).getBook().getAuthor());
        assertEquals("Taras Shevchenko", sorted.get(2).getBook().getAuthor());
    }

    @Test
    void shouldSortByPriceAscendingUsingAnonymousComparator() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new AudioBook("Atomic Habits", "James Clear", 2018, 320.0, Genre.NON_FICTION, "James Clear", 335), 2);
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);

        Comparator<LibraryItem> priceCmp = new Comparator<LibraryItem>() {
            @Override
            public int compare(LibraryItem o1, LibraryItem o2) {
                return Double.compare(o1.getBook().getPrice(), o2.getBook().getPrice());
            }
        };

        ArrayList<LibraryItem> sorted = library.getSortedItems(priceCmp);
        assertEquals(150.0, sorted.get(0).getBook().getPrice());
        assertEquals(200.0, sorted.get(1).getBook().getPrice());
        assertEquals(320.0, sorted.get(2).getBook().getPrice());
    }

    @Test
    void shouldSortByYearDescendingUsingAnonymousComparator() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new AudioBook("Atomic Habits", "James Clear", 2018, 320.0, Genre.NON_FICTION, "James Clear", 335), 2);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);

        Comparator<LibraryItem> yearCmp = new Comparator<LibraryItem>() {
            @Override
            public int compare(LibraryItem o1, LibraryItem o2) {
                return Integer.compare(o2.getBook().getYear(), o1.getBook().getYear());
            }
        };

        ArrayList<LibraryItem> sorted = library.getSortedItems(yearCmp);
        assertEquals(2018, sorted.get(0).getBook().getYear());
        assertEquals(1965, sorted.get(1).getBook().getYear());
        assertEquals(1840, sorted.get(2).getBook().getYear());
    }
}