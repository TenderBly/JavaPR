import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Manages JDBC connections and inserts Book hierarchy objects into a relational database.
 */
public class DatabaseManager {
    private String url;
    private String user;
    private String password;

    public DatabaseManager(String configFilePath) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configFilePath)) {
            props.load(fis);
        }
        this.url = props.getProperty("db.url");
        this.user = props.getProperty("db.user");
        this.password = props.getProperty("db.password");

        if (this.url == null || this.user == null || this.password == null) {
            throw new IllegalArgumentException("Missing required database properties (db.url, db.user, db.password).");
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            return false;
        }
    }

    public void insertBook(Book book, int quantity) {
        String sql = "INSERT INTO books ("
                + "type, title, author, year, price, genre, quantity, "
                + "file_size_mb, file_format, page_count, weight_kg, "
                + "narrator, duration_minutes, has_video_content, has_quizzes"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getType());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setInt(4, book.getYear());
            stmt.setDouble(5, book.getPrice());
            stmt.setString(6, book.getGenre().name());
            stmt.setInt(7, quantity);

            stmt.setNull(8, Types.DOUBLE);
            stmt.setNull(9, Types.VARCHAR);
            stmt.setNull(10, Types.INTEGER);
            stmt.setNull(11, Types.DOUBLE);
            stmt.setNull(12, Types.VARCHAR);
            stmt.setNull(13, Types.INTEGER);
            stmt.setNull(14, Types.BOOLEAN);
            stmt.setNull(15, Types.BOOLEAN);

            if (book instanceof InteractiveEBook) {
                InteractiveEBook ieb = (InteractiveEBook) book;
                stmt.setDouble(8, ieb.getFileSizeMB());
                stmt.setString(9, ieb.getFileFormat());
                stmt.setBoolean(14, ieb.isHasVideoContent());
                stmt.setBoolean(15, ieb.isHasQuizzes());
            } else if (book instanceof EBook) {
                EBook eb = (EBook) book;
                stmt.setDouble(8, eb.getFileSizeMB());
                stmt.setString(9, eb.getFileFormat());
            } else if (book instanceof PaperBook) {
                PaperBook pb = (PaperBook) book;
                stmt.setInt(10, pb.getPageCount());
                stmt.setDouble(11, pb.getWeightKg());
            } else if (book instanceof AudioBook) {
                AudioBook ab = (AudioBook) book;
                stmt.setString(12, ab.getNarrator());
                stmt.setInt(13, ab.getDurationMinutes());
            }

            stmt.executeUpdate();
            System.out.println("Book record successfully inserted into database (type=" + book.getType() + ").");
        } catch (SQLException e) {
            System.out.println("SQL error during INSERT: " + e.getMessage());
        }
    }
}