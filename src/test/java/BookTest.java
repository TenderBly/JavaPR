import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldIncreaseQuantityWhenAddingExistingBook() {
        Library library = new Library("Test Library", "Main St");
        Book b1 = new Book("Clean Code", "Robert Martin", 2008, 450.0, Genre.SCIENCE);
        Book b2 = new Book("Clean Code", "Robert Martin", 2008, 450.0, Genre.SCIENCE);

        library.addNewBook(b1, 3);
        library.addNewBook(b2, 2);

        assertEquals(1, library.getItems().size());
        assertEquals(Integer.valueOf(5), library.getItems().get(0).getQuantity());
    }

    @Test
    void shouldSearchBooksInLibrary() {
        Library library = new Library("Test Library", "Main St");
        library.addNewBook(new Book("Clean Code", "Robert Martin", 2008, 450.0, Genre.SCIENCE), 2);
        library.addNewBook(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"), 4);
        library.addNewBook(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5), 1);

        ArrayList<LibraryItem> byTitle = library.searchByTitle("dune");
        assertEquals(1, byTitle.size());
        assertEquals(Integer.valueOf(4), byTitle.get(0).getQuantity());

        ArrayList<LibraryItem> byGenre = library.searchByGenre(Genre.SCIENCE);
        assertEquals(1, byGenre.size());

        ArrayList<LibraryItem> byPrice = library.searchByMaxPrice(250.0);
        assertEquals(2, byPrice.size());
    }

    @Test
    void shouldThrowExceptionForInvalidQuantity() {
        Library library = new Library("Test Library", "Main St");
        Book book = new Book("Title", "Author", 2024, 100.0, Genre.FICTION);
        assertThrows(IllegalArgumentException.class, () -> {
            library.addNewBook(book, 0);
        });
    }
}