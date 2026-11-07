import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    private static final String FILE_NAME = "input.json";

    public static void saveToJson(ArrayList<Book> library) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(library, writer);
            System.out.println("Library successfully saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static ArrayList<Book> loadFromJson() {
        ArrayList<Book> library = new ArrayList<>();
        Gson gson = new Gson();
        
        try (FileReader reader = new FileReader(FILE_NAME)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String type = obj.has("type") ? obj.get("type").getAsString() : "Book";
                
                Book book;
                switch (type) {
                    case "EBook":
                        book = gson.fromJson(obj, EBook.class);
                        break;
                    case "PaperBook":
                        book = gson.fromJson(obj, PaperBook.class);
                        break;
                    case "AudioBook":
                        book = gson.fromJson(obj, AudioBook.class);
                        break;
                    case "InteractiveEBook":
                        book = gson.fromJson(obj, InteractiveEBook.class);
                        break;
                    default:
                        book = gson.fromJson(obj, Book.class);
                }
                library.add(book);
            }
            System.out.println("Library successfully loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Save file not found. Starting with an empty library.");
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        return library;
    }
}