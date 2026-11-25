import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    private static final String FILE_NAME = "input.txt";

    public static void saveLibrary(Library library) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(library.getItems(), writer);
            System.out.println("Library successfully saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void loadLibrary(Library library) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(FILE_NAME)) {
            JsonElement parsed = JsonParser.parseReader(reader);
            if (parsed == null || !parsed.isJsonArray()) {
                System.out.println("File is empty or has invalid format.");
                return;
            }
            JsonArray jsonArray = parsed.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject itemObj = element.getAsJsonObject();
                JsonObject bookObj = itemObj.has("book") ? itemObj.getAsJsonObject("book") : itemObj;
                int quantity = itemObj.has("quantity") ? itemObj.get("quantity").getAsInt() : 1;

                String type = bookObj.has("type") ? bookObj.get("type").getAsString() : "Book";
                Book book;
                switch (type) {
                    case "EBook":
                        book = gson.fromJson(bookObj, EBook.class);
                        break;
                    case "PaperBook":
                        book = gson.fromJson(bookObj, PaperBook.class);
                        break;
                    case "AudioBook":
                        book = gson.fromJson(bookObj, AudioBook.class);
                        break;
                    case "InteractiveEBook":
                        book = gson.fromJson(bookObj, InteractiveEBook.class);
                        break;
                    default:
                        System.out.println("Skipping unsupported or abstract book type: " + type);
                        continue;
                }
                library.addNewBook(book, quantity);
            }
            System.out.println("Library successfully loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("File " + FILE_NAME + " not found. Starting with an empty library.");
        } catch (Exception e) {
            System.out.println("Error reading " + FILE_NAME + ": " + e.getMessage());
        }
    }
}