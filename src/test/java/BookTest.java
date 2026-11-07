import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldThrowExceptionWhenInvalidValueInSetter() {
        Book book = new Book("Кобзар", "Тарас Шевченко", 1840, 250.0);
        
        // Перевіряємо, що встановлення від'ємної ціни кидає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            book.setPrice(-50.0);
        });
    }

    @Test
    void shouldThrowExceptionWhenInvalidConstructorData() {
        // Перевіряємо, що створення об'єкта з порожньою назвою кидає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Автор", 2020, 100.0);
        });
    }
}