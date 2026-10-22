import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void add(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("book is required");
        }
        for (Book existing : books) {
            if (existing.getIsbn().equals(book.getIsbn())) {
                throw new IllegalArgumentException("isbn " + book.getIsbn() + " already exists");
            }
        }
        books.add(book);
    }

    public void remove(String isbn) {
        books.remove(findByIsbn(isbn));
    }

    public Book findByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        throw new IllegalArgumentException("no book with isbn " + isbn);
    }

    public List<Book> search(String text) {
        List<Book> found = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(text.toLowerCase())) {
                found.add(book);
            }
        }
        return found;
    }

    public List<Book> listAll() {
        return new ArrayList<>(books);
    }

    public int size() {
        return books.size();
    }
}
