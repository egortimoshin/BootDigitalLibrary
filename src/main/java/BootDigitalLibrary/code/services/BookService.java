package BootDigitalLibrary.code.services;

import BootDigitalLibrary.code.models.Person;
import BootDigitalLibrary.code.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import BootDigitalLibrary.code.repositories.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    private final PersonService personService;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, PersonService personService, JdbcTemplate jdbcTemplate) {
        this.bookRepository = bookRepository;
        this.personService = personService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book findById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(int id) {
        bookRepository.deleteById(id);
    }

    public void insertBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book newBook) {
        Book oldBook = findById(newBook.getId());
        oldBook.setAuthor(newBook.getAuthor());
        oldBook.setTitle(newBook.getTitle());
        oldBook.setYear(newBook.getYear());
        oldBook.setQuantity(newBook.getQuantity());
        bookRepository.save(oldBook);
    }

    public void giveBookToPerson(Book book, Person person) {
        jdbcTemplate.update("UPDATE BOOK SET quantity = ? WHERE id = ?",
                book.getQuantity() - 1, book.getId());
        jdbcTemplate.update("INSERT INTO PERSON_BOOK(person_id, book_id) SELECT ?, ? WHERE NOT EXISTS (SELECT 1 FROM PERSON_BOOK WHERE person_id = ? AND book_id = ?)",
                person.getId(), book.getId(), person.getId(), book.getId());
    }


    public void takeBookFromPerson(Person person, Book book) {
        jdbcTemplate.update("DELETE FROM PERSON_BOOK WHERE person_id = ? AND book_id = ?",
                person.getId(), book.getId());
        jdbcTemplate.update("UPDATE BOOK SET quantity = ? WHERE id = ?",
                book.getQuantity() + 1, book.getId());
    }

    public List<Book> getBooksReadByPerson(int person_id) {
        List<Book> list = bookRepository.findBooksReadByPerson(person_id);
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            book = bookRepository.findById(book.getId()).orElse(null);
            list.set(i, book);
        }
        return list;
    }
}
