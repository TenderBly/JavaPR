import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldCreateEBookSuccessfully() {
        EBook ebook = new EBook("Java Guide", "Author", 2024, 100, Genre.SCIENCE, 5.5, "PDF");
        assertEquals("PDF", ebook.toString().contains("PDF") ? "PDF" : "");
    }

    @Test
    void shouldThrowExceptionForInvalidAudioBookDuration() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AudioBook("Title", "Author", 2024, 100, Genre.FICTION, "Narrator", -50);
        });
    }

    @Test
    void shouldCreateInteractiveEBook() {
        InteractiveEBook ieb = new InteractiveEBook("Title", "Author", 2024, 100, Genre.SCIENCE, 10.0, "EPUB", true, true);
        assertTrue(ieb.toString().contains("hasVideoContent=true"));
    }
}