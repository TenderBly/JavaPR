public class InteractiveEBook extends EBook {
    private boolean hasVideoContent;
    private boolean hasQuizzes;

    public InteractiveEBook(String title, String author, int year, double price, Genre genre, double fileSizeMB, String fileFormat, boolean hasVideoContent, boolean hasQuizzes) {
        super(title, author, year, price, genre, fileSizeMB, fileFormat);
        this.type = "InteractiveEBook";
        this.hasVideoContent = hasVideoContent;
        this.hasQuizzes = hasQuizzes;
    }

    @Override
    public String toString() {
        return "InteractiveEBook{" + super.toString() + ", hasVideoContent=" + hasVideoContent + ", hasQuizzes=" + hasQuizzes + "}";
    }
}