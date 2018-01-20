package com.ohgianni.tin.Service;

import com.ohgianni.tin.DTO.BookDTO;
import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Enum.BookStatus;
import com.ohgianni.tin.Exception.BookNotFoundException;
import com.ohgianni.tin.Repository.BookRepository;
import com.ohgianni.tin.Repository.ClientRepository;
import com.ohgianni.tin.Repository.PublisherRepository;
import com.ohgianni.tin.Repository.ReservationRepository;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.*;

import static com.ohgianni.tin.Enum.BookStatus.AVAILABLE;
import static com.ohgianni.tin.Enum.BookStatus.RESERVED;


@Service
public class BookService {

    private BookRepository bookRepository;

    private ReservationRepository reservationRepository;

    private ClientRepository clientRepository;

    private PublisherRepository publisherRepository;

    private ImageService imageService;

    @Autowired
    public BookService(BookRepository bookRepository,
                       ReservationRepository reservationRepository,
                       ClientRepository clientRepository,
                       PublisherRepository publisherRepository,
                       ImageService imageService) {
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.publisherRepository = publisherRepository;
        this.imageService = imageService;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();

        return books;
    }

    @Transactional
    public List<Book> getAllBooksDistinct() {
        List<Book> books = getAllBooks();
        List<Book> booksDistinct = getBooksDistinct(books);
        setSpecimenInformation(books, booksDistinct);

        return booksDistinct;
    }

    public List<Book> getAllBooksByIsbn(Long isbn) {
        return bookRepository.findAllByIsbn(isbn);
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

    public Book getBookByIsbn(Long isbn) {

        return getAllBooksDistinct().stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(new Book());
    }

    @Transactional
    public void deleteBook(Long id, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

            if(canBeDeleted(book)){
                bookRepository.delete(book);
                redirectAttributes.addFlashAttribute("success", "Książka o ID " + id + " została pomyślnie usunięta");
            } else {
                redirectAttributes.addFlashAttribute("error", "Książka o ID " + id + " nie może zostać usunięta, ponieważ ma status " + book.getStatus());
            }

        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Książka o ID " + id + " nie została znaleziona");
        }
    }

    @Transactional
    public void update(BookDTO bookDto, Long isbn) {
        List<Book> books = bookRepository.findAllByIsbn(isbn);
        books.forEach(book -> {
            try {
                update(book, bookDto);
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
        });

    }

    private void update(Book book, BookDTO bookDto) throws NotFound {
        Book bookFromDto = bookDto.getBook();

        book.setTitle(bookFromDto.getTitle());
        book.setEdition(bookFromDto.getEdition());
        book.setIsbn(bookFromDto.getIsbn());
        book.setPages(bookFromDto.getPages());
        book.setPublisher(getPublisher(bookDto));
        book.setCoverType((bookDto.getBook().getCoverType()));
        book.setCoverImage(getCoverImage(bookDto));

        bookRepository.save(book);
    }

    private String getCoverImage(BookDTO bookDto) {
        MultipartFile file = bookDto.getBook().getMultipartImage();

        if(file.getSize() == 0) {
            return bookDto.getBook().getCoverImage();
        } else {
            return imageService.saveBookCover(file);
        }
    }

    private Publisher getPublisher(BookDTO bookDTO) throws NotFound {
        Long id = Long.parseLong(bookDTO.getPublisher());
        return publisherRepository.findById(id).orElseThrow(NotFound::new);
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

    private List<Book> setSpecimenInformation(List<Book> books, List<Book> booksDistinct) {
        Map<Long, Integer> specimenAvailable = getSpecimenAvailable(books);
        Map<Long, Integer> specimenTotal = getSpecimenTotal(books);

        booksDistinct.forEach(book -> {
            book.setAvailableSpecimen(specimenAvailable.get(book.getIsbn()));
            book.setTotalSpecimen(specimenTotal.get(book.getIsbn()));
        });

        return books;
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

    private boolean canBeDeleted(Book book) {
        return (book.getStatus() == AVAILABLE);
    }

}
