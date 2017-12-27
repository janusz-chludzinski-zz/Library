package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Exception.BookNotFoundException;
import com.ohgianni.tin.Repository.BookRepository;
import com.ohgianni.tin.Repository.ClientRepository;
import com.ohgianni.tin.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ohgianni.tin.Enum.BookStatus.AVAILABLE;
import static com.ohgianni.tin.Enum.BookStatus.RESERVED;


@Service
public class BookService {

    private BookRepository bookRepository;

    private ReservationRepository reservationRepository;

    private ClientRepository clientRepository;

    private ImageService imageService;

    private static final String BASE64_PREFIX = "data:image/jpeg;base64,";


    @Autowired
    public BookService(BookRepository bookRepository, ReservationRepository reservationRepository, ClientRepository clientRepository, ImageService imageService) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.imageService = imageService;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public List<Book>   getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        books.forEach(book -> book.setImageUrl(imageService.getBookCoverUrl(book)));

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

        setStatusAndSave(book, RESERVED);

        return reservationRepository.save(new Reservation(books.get(0), client));

    }

    public Book setStatusAndSave(Book book, BookStatus status) {
        book.setStatus(status);
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
