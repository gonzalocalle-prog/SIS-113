public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private boolean available = true;

    public Book(String isbn, String title, String author) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("isbn is required");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is required");
        }
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Book(String isbn, String title) {
        this(isbn, title, "Unknown author");
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void lend() {
        if (!available) {
            throw new IllegalArgumentException(title + " is already lent");
        }
        available = false;
    }

    public void giveBack() {
        if (available) {
            throw new IllegalArgumentException(title + " was not lent");
        }
        available = true;
    }

    @Override
    public String toString() {
        return isbn + " | " + title + " | " + author + " | " + (available ? "available" : "lent");
    }
}
