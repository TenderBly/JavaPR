import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldCreateEBookSuccessfully() {
        EBook ebook = new EBook("Java Guide", "Author", 2024, 100, Genre.SCIENCE, 5.5, "PDF");
        assertTrue(ebook.toString().contains("PDF"));
    }

    @Test
    void shouldThrowExceptionForInvalidAudioBookDuration() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AudioBook("Title", "Author", 2024, 100, Genre.FICTION, "Narrator", -50);
        });
    }

    @Test
    void shouldSearchBooksWithoutModifyingCollection() {
        ArrayList<Book> library = new ArrayList<Book>();
        library.add(new Book("Clean Code", "Robert Martin", 2008, 450.0, Genre.SCIENCE));
        library.add(new EBook("Dune", "Frank Herbert", 1965, 200.0, Genre.FANTASY, 3.2, "EPUB"));
        library.add(new PaperBook("Kobzar", "Taras Shevchenko", 1840, 150.0, Genre.HISTORY, 400, 0.5));

        ArrayList<Book> byTitle = Main.searchByTitle(library, "dune");
        assertEquals(1, byTitle.size());
        assertEquals("Dune", byTitle.get(0).getTitle());

        ArrayList<Book> byGenre = Main.searchByGenre(library, Genre.SCIENCE);
        assertEquals(1, byGenre.size());

        ArrayList<Book> byPrice = Main.searchByMaxPrice(library, 250.0);
        assertEquals(2, byPrice.size());

        // Original collection must remain unchanged
        assertEquals(3, library.size());
    }
}