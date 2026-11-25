import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldHandleEmptyLibrarySortingWithLambda() {
        Library library = new Library("Test Library", "Main St");
        Comparator<LibraryItem> titleCmp = (o1, o2) ->
                o1.getBook().getTitle().compareToIgnoreCase(o2.getBook().getTitle());

        ArrayList<LibraryItem> sorted = library.getSortedItems(titleCmp);
        assertTrue(sorted.isEmpty());
    }

    @Test
    void shouldHandleSingleElementSortingWithLambda() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);

        Comparator<LibraryItem> priceCmp = (o1, o2) ->
                Double.compare(o1.getBook().getPrice(), o2.getBook().getPrice());

        ArrayList<LibraryItem> sorted = library.getSortedItems(priceCmp);
        assertEquals(1, sorted.size());
        assertEquals("Kobzar", sorted.get(0).getBook().getTitle());
    }

    @Test
    void shouldSortByAuthorUsingLambda() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);
        library.addNewBook(new AudioBook("Atomic Habits", "James Clear", 2018, 320.0, Genre.NON_FICTION, "James Clear", 335), 2);

        Comparator<LibraryItem> authorCmp = (o1, o2) ->
                o1.getBook().getAuthor().compareToIgnoreCase(o2.getBook().getAuthor());

        ArrayList<LibraryItem> sorted = library.getSortedItems(authorCmp);
        assertEquals("Frank Herbert", sorted.get(0).getBook().getAuthor());
        assertEquals("James Clear", sorted.get(1).getBook().getAuthor());
        assertEquals("Taras Shevchenko", sorted.get(2).getBook().getAuthor());
    }

    @Test
    void shouldSortByPriceAscendingWithEqualPricesUsingLambda() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new AudioBook("Zebra Guide", "Author A", 2020, 200.0, Genre.NON_FICTION, "Narrator", 120), 1);
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new EBook("Alpha Guide", "Author B", 2019, 200.0, Genre.SCIENCE, 2.5, "PDF"), 2);

        Comparator<LibraryItem> priceCmp = (o1, o2) -> {
            int res = Double.compare(o1.getBook().getPrice(), o2.getBook().getPrice());
            if (res != 0) {
                return res;
            }
            return o1.getBook().getTitle().compareToIgnoreCase(o2.getBook().getTitle());
        };

        ArrayList<LibraryItem> sorted = library.getSortedItems(priceCmp);
        assertEquals(150.0, sorted.get(0).getBook().getPrice());
        assertEquals("Alpha Guide", sorted.get(1).getBook().getTitle());
        assertEquals("Zebra Guide", sorted.get(2).getBook().getTitle());
    }

    @Test
    void shouldSortByYearDescendingUsingLambda() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);
        library.addNewBook(new AudioBook("Atomic Habits", "James Clear", 2018, 320.0, Genre.NON_FICTION, "James Clear", 335), 2);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);

        Comparator<LibraryItem> yearCmp = (o1, o2) ->
                Integer.compare(o2.getBook().getYear(), o1.getBook().getYear());

        ArrayList<LibraryItem> sorted = library.getSortedItems(yearCmp);
        assertEquals(2018, sorted.get(0).getBook().getYear());
        assertEquals(1965, sorted.get(1).getBook().getYear());
        assertEquals(1840, sorted.get(2).getBook().getYear());
    }
    @Test
    void shouldAssignUniqueUuidAndFindByUuid() {
        Library library = new Library("Test Library", "Main St");
        EBook ebook = new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB");
        PaperBook paper = new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5);

        assertNotNull(ebook.getUuid());
        assertNotNull(paper.getUuid());
        assertNotEquals(ebook.getUuid(), paper.getUuid());

        library.addNewBook(ebook, 2);
        library.addNewBook(paper, 1);

        LibraryItem found = library.searchByUuid(ebook.getUuid().toString());
        assertNotNull(found);
        assertEquals("Dune", found.getBook().getTitle());
    }

    @Test
    void shouldThrowExceptionForInvalidUuidFormat() {
        Library library = new Library("Test Library", "Main St");
        assertThrows(IllegalArgumentException.class, () -> {
            library.searchByUuid("not-a-valid-uuid");
        });
    }

    @Test
    void shouldUpdateExistingBookInLibrary() {
        Library library = new Library("Test Library", "Main St");
        EBook original = new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB");
        EBook updated = new EBook("Dune Messiah", "Frank Herbert", 1969, 250.0, Genre.FANTASY, 3.2, "EPUB");

        library.addNewBook(original, 3);
        boolean result = library.update(original, updated);

        assertTrue(result);
        assertEquals(1, library.getItems().size());
        assertEquals("Dune Messiah", library.getItems().get(0).getBook().getTitle());
        assertEquals(250.0, library.getItems().get(0).getBook().getPrice());
        assertEquals(Integer.valueOf(3), library.getItems().get(0).getQuantity());
    }

    @Test
    void shouldReturnFalseWhenUpdatingNonExistentBook() {
        Library library = new Library("Test Library", "Main St");
        EBook missing = new EBook("Unknown", "Author", 2020, 100.0, Genre.FICTION, 1.0, "PDF");
        EBook replacement = new EBook("New Title", "Author", 2021, 120.0, Genre.FICTION, 1.0, "PDF");

        assertFalse(library.update(missing, replacement));
    }

    @Test
    void shouldDeleteExistingBookFromLibrary() {
        Library library = new Library("Test Library", "Main St");
        PaperBook book1 = new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5);
        EBook book2 = new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB");

        library.addNewBook(book1, 2);
        library.addNewBook(book2, 1);

        boolean deleted = library.delete(book1);
        assertTrue(deleted);
        assertEquals(1, library.getItems().size());
        assertEquals("Dune", library.getItems().get(0).getBook().getTitle());
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentBook() {
        Library library = new Library("Test Library", "Main St");
        PaperBook missing = new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5);

        assertFalse(library.delete(missing));
    }
}