public class AudioBook extends Book {
    private String narrator;
    private int durationMinutes;

    public AudioBook(String title, String author, int year, double price, Genre genre, String narrator, int durationMinutes) {
        super(title, author, year, price, genre);
        setNarrator(narrator);
        setDurationMinutes(durationMinutes);
    }

    public void setNarrator(String narrator) {
        if (narrator == null || narrator.trim().isEmpty()) throw new IllegalArgumentException("Narrator cannot be empty.");
        this.narrator = narrator;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) throw new IllegalArgumentException("Duration must be positive.");
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String toString() {
        return "AudioBook{" + super.toString() + ", narrator='" + narrator + "', durationMinutes=" + durationMinutes + "}";
    }
}