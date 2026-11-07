public class PaperBook extends Book {
    private int pageCount;
    private double weightKg;

    public PaperBook(String title, String author, int year, double price, Genre genre, int pageCount, double weightKg) {
        super(title, author, year, price, genre);
        this.type = "PaperBook";
        setPageCount(pageCount);
        setWeightKg(weightKg);
    }

    public void setPageCount(int pageCount) {
        if (pageCount <= 0) throw new IllegalArgumentException("Page count must be positive.");
        this.pageCount = pageCount;
    }

    public void setWeightKg(double weightKg) {
        if (weightKg <= 0) throw new IllegalArgumentException("Weight must be positive.");
        this.weightKg = weightKg;
    }

    @Override
    public String toString() {
        return "PaperBook{" + super.toString() + ", pageCount=" + pageCount + ", weightKg=" + weightKg + "'}";
    }
}