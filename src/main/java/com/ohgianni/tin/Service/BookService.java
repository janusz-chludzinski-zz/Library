package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Exception.BookNotFoundException;
import com.ohgianni.tin.Repository.BookRepository;
import com.ohgianni.tin.Repository.ClientRepository;
import com.ohgianni.tin.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ohgianni.tin.Enum.BookStatus.*;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

import javax.transaction.Transactional;
import java.util.*;


@Service
public class BookService {

    private BookRepository bookRepository;

    private ReservationRepository reservationRepository;

    private ClientRepository clientRepository;

    private static final String BASE64_PREFIX = "data:image/jpeg;base64,";


    @Autowired
    public BookService(BookRepository bookRepository, ReservationRepository reservationRepository, ClientRepository clientRepository) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public List<Book>   getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        books.forEach(this::encodeBookCover);

        return books;
    }

    @Transactional
    public List<Book> getAllBooksDistinct() {
        List<Book> books = getAllBooks();
        List<Book> booksDistinct = getBooksDistinct(books);
        setSpecimenInformation(books, booksDistinct);

        return booksDistinct;
    }

    private List<Book> getBooksDistinct(List<Book> books) {
        Map<Long, Book> booksDistinct = new HashMap<>();
        books.forEach(book -> {
            if (!booksDistinct.containsKey(book.getIsbn())) {
                booksDistinct.put(book.getIsbn(), book);
            }
        });

        return new ArrayList<>(booksDistinct.values());
    }

    public Book getBookByIsbn(Long isbn) {

        return getAllBooksDistinct().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(new Book());
    }

    public void encodeBookCover(Book book) {
        book.setImageUrl(BASE64_PREFIX + encodeBase64String(book.getCoverImage()));
    }

    private List<Book> setSpecimenInformation(List<Book> books, List<Book> booksDistinct) {
        Map<Long, Integer> specimenAvailable = getSpecimenAvailable(books);
        Map<Long, Integer> specimenTotal = getSpecimenTotal(books);

        booksDistinct.forEach(book -> {
            book.setAvailableSpecimen(specimenAvailable.get(book.getIsbn()));
            book.setTotalSpecimen(specimenTotal.get(book.getIsbn()));
        });

        return books;
    }

    @Transactional
    public Reservation reserveBook(Long isbn, String email) throws BookNotFoundException {

        Client client = clientRepository.findByEmail(email);
        List<Book> books = bookRepository.findByIsbnAndStatus(isbn, AVAILABLE);

        Book book = books.stream().findFirst().orElseThrow(BookNotFoundException::new);

        setStatusAndSave(book);

        return reservationRepository.save(new Reservation(books.get(0), client));

    }

    private Book setStatusAndSave(Book book) {
        book.setStatus(RESERVED);
        return bookRepository.save(book);
    }

    private Map<Long, Integer> getSpecimenTotal(List<Book> books) {
        Map<Long, Integer> result = new HashMap<>();

        books.forEach(book -> {
            Long isbn = book.getIsbn();

            if (!result.containsKey(isbn)) {
                result.put(isbn, 1);
            } else {
                result.put(book.getIsbn(), result.get(isbn) + 1);
            }
        });

        return result;
    }

    private Map<Long, Integer> getSpecimenAvailable(List<Book> books) {
        Map<Long, Integer> result = new HashMap<>();

        books.forEach(book -> {
            Long isbn = book.getIsbn();

            if (!result.containsKey(isbn)) {
                result.put(isbn, 0);
            }

            if (book.getStatus() == AVAILABLE) {
                result.put(isbn, result.get(isbn) + 1);
            }

        });
        return result;
    }
}
