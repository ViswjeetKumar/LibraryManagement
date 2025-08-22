package service;

import dao.BookDAO;
import model.Book;
import java.util.List;

public class LibraryService {
    private final BookDAO bookDAO;

    public LibraryService() {
        this.bookDAO = new BookDAO();
    }

    public void addBook(String isbn, String title, String author, int publicationYear, boolean isAvailable) {
        Book book = new Book(isbn, title, author, publicationYear, isAvailable);
        bookDAO.addBook(book);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book findBookByIsbn(String isbn) {
        return bookDAO.findBookByIsbn(isbn);
    }

    public void removeBook(String isbn) {
        bookDAO.removeBook(isbn);
    }
}