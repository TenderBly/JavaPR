import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldCreateEBookSuccessfully() {
        EBook ebook = new EBook("Java Guide", "Author", 2024, 100, Genre.SCIENCE, 5.5, "PDF");
        assertEquals("PDF", ebook.toString().contains("PDF") ? "PDF" : "");
    }

    @Test
    void shouldThrowExceptionForInvalidPaperBookWeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PaperBook("Title", "Author", 2024, 100, Genre.FICTION, 300, -1.5);
        });
    }
}