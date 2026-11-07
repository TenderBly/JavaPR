public class EBook extends Book {
    private double fileSizeMB;
    private String fileFormat;

    public EBook(String title, String author, int year, double price, Genre genre, double fileSizeMB, String fileFormat) {
        super(title, author, year, price, genre);
        setFileSizeMB(fileSizeMB);
        setFileFormat(fileFormat);
    }

    public void setFileSizeMB(double fileSizeMB) {
        if (fileSizeMB <= 0) throw new IllegalArgumentException("File size must be positive.");
        this.fileSizeMB = fileSizeMB;
    }

    public void setFileFormat(String fileFormat) {
        if (fileFormat == null || fileFormat.trim().isEmpty()) throw new IllegalArgumentException("Format cannot be empty.");
        this.fileFormat = fileFormat;
    }

    @Override
    public String toString() {
        return "EBook{" + super.toString() + ", fileSizeMB=" + fileSizeMB + ", fileFormat='" + fileFormat + "'}";
    }
}