import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Book book = new Book("Kobzar", "Taras Shevchenko", 1840, 250.0, Genre.HISTORY);
        assertThrows(IllegalArgumentException.class, () -> {
            book.setPrice(-50.0);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Author", 2020, 100.0, Genre.FICTION);
        });
    }
    
    @Test
    void shouldCopyBookCorrectly() {
        Book original = new Book("Dune", "Frank Herbert", 1965, 300.0, Genre.SCIENCE);
        Book copy = new Book(original);
        assertEquals(original, copy);
        assertNotSame(original, copy); // Ensure they are different objects in memory
    }
}