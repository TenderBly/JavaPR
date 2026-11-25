import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryExceptionTest {

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingBook() {
        Library library = new Library("Test Library", "Main St");
        PaperBook nonExisting = new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5);

        assertThrows(ObjectNotFoundException.class, () -> {
            library.delete(nonExisting);
        });
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenUpdatingNonExistingBook() {
        Library library = new Library("Test Library", "Main St");
        EBook nonExisting = new EBook("Unknown", "Author", 2020, 100.0, Genre.FICTION, 1.5, "PDF");
        EBook replacement = new EBook("New Title", "Author", 2021, 120.0, Genre.FICTION, 1.5, "PDF");

        assertThrows(ObjectNotFoundException.class, () -> {
            library.update(nonExisting, replacement);
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenCreatingBookWithNegativePrice() {
        assertThrows(InvalidFieldValueException.class, () -> {
            new EBook("Dune", "Frank Herbert", 1965, -50.0, Genre.FANTASY, 3.2, "EPUB");
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenSettingEmptyTitle() {
        PaperBook book = new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5);

        assertThrows(InvalidFieldValueException.class, () -> {
            book.setTitle("   ");
        });
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenAddingNonPositiveQuantity() {
        Library library = new Library("Test Library", "Main St");
        EBook book = new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB");

        assertThrows(InvalidFieldValueException.class, () -> {
            library.addNewBook(book, 0);
        });
    }
}