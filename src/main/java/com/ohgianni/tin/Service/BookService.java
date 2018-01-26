package com.ohgianni.tin.Service;

import static com.ohgianni.tin.Enum.BookStatus.AVAILABLE;
import static com.ohgianni.tin.Enum.BookStatus.DELETED;
import static com.ohgianni.tin.Enum.BookStatus.RESERVED;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Service
public class BookService {

    private BookRepository bookRepository;

    private ReservationRepository reservationRepository;

    private ClientRepository clientRepository;

    private PublisherRepository publisherRepository;

    private ImageService imageService;

    private Book book;

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
        List<Book> books = bookRepository.findAllByIsbn(isbn);

        return books.stream()
                .filter(book -> book.getStatus() != BookStatus.DELETED)
                .collect(Collectors.toList());
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
                setStatusAndSave(book, DELETED);
                redirectAttributes.addFlashAttribute("success", "Książka o ID " + id + " została pomyślnie usunięta");
            } else {
                redirectAttributes.addFlashAttribute("error", "Książka o ID " + id + " nie może zostać usunięta, ponieważ ma status " + book.getStatus());
            }

        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Książka o ID " + id + " nie została znaleziona");
        }
    }

    @Transactional
    public void addBook(BookDTO bookDTO) {
        Book newBook = bookDTO.getBook();

        String coverUrl = imageService.saveBookCover(newBook.getMultipartImage());
        newBook.setCoverImage(coverUrl);

        Long publisherId = Long.parseLong(bookDTO.getPublisher());
        publisherRepository.findById(publisherId).ifPresent(newBook::setPublisher);

        newBook.setStatus(AVAILABLE);

        saveBook(newBook);
    }

    public void addBookItem(Long isbn, RedirectAttributes redirectAttributes) {
        try {
            Book book = bookRepository.findAllByIsbn(isbn).stream().findFirst().orElseThrow(BookNotFoundException::new);
            Book newBook = new Book(book);

            bookRepository.save(newBook);

            redirectAttributes.addFlashAttribute("success", "Egzemplarz został pomyślnie dodany, ID: " + newBook.getBookId());

        } catch (BookNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Nie znaleziono książki o ISBN " + isbn);
        }
    }

    public void validateBook(@Valid BookDTO bookDTO, RedirectAttributes redirectAttributes) {
        book = bookDTO.getBook();

        validateAuthors(book, redirectAttributes);
        validateIfFieldsAreEmpty(redirectAttributes);
        validateIsbn(bookDTO, redirectAttributes);
        validateMultiparImage(book, redirectAttributes);
        validatePublisher(bookDTO, redirectAttributes);
        validateCoverType(book, redirectAttributes);

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

    public boolean isUpdateValid(BookDTO bookDto, RedirectAttributes redirectAttributes) {
        book = bookDto.getBook();
        validateIfFieldsAreEmpty(redirectAttributes);
        validateIsbn(bookDto, redirectAttributes);

        return redirectAttributes.getFlashAttributes().isEmpty();
    }

    private void validateAuthors(Book book, RedirectAttributes redirectAttributes) {
        if(book.getAuthors().size() == 0) {
            redirectAttributes.addFlashAttribute("authors", "Proszę wybrać przynajmniej jednego autora książki");
        }
    }

    private void validateCoverType(Book book, RedirectAttributes redirectAttributes) {
        String coverType = ofNullable(valueOf(book.getCoverType())).orElse("0");
        if(coverType.equals("null") || coverType.isEmpty()) {
            redirectAttributes.addFlashAttribute("cover", "Proszę wybrać typ okładki");
        }
    }

    private void validatePublisher(BookDTO bookDTO, RedirectAttributes redirectAttributes) {
        String publisher = ofNullable(bookDTO.getPublisher()).orElse("0");
        if(publisher.equals("0") || publisher.isEmpty()) {
            redirectAttributes.addFlashAttribute("publisher", "Proszę wybrać wydawcę");
        }
    }

    private void validateIsbn(BookDTO bookDTO, RedirectAttributes redirectAttributes) {
        if(isNotSameIsbn(bookDTO)) {
            if(bookRepository.existsByIsbn(book.getIsbn())){
                redirectAttributes.addFlashAttribute("isbnn", "W bazie znajduje sie już książka o ISBN " + book.getIsbn());
            }
        }
    }

    private boolean isNotSameIsbn(BookDTO bookDTO) {
        Optional<Long> currentIsbn = ofNullable(bookDTO.getCurrentIsbn());

        if(currentIsbn.isPresent()) {
            return !currentIsbn.get().equals(bookDTO.getBook().getIsbn());
        }

        return true;
    }

    private void validateMultiparImage(Book book, RedirectAttributes redirectAttributes) {
        if(book.getMultipartImage().isEmpty()) {
            redirectAttributes.addFlashAttribute("image", "Proszę wybrać okładkę dla książki");
        }
    }

    private void validateIfFieldsAreEmpty(RedirectAttributes redirectAttributes) {
        String empty = "Pole nie może być puste";

        if(book.getTitle().trim().isEmpty()){
            redirectAttributes.addFlashAttribute("title", empty);
        }

        String edition = valueOf(book.getEdition()).trim();
        if(edition.isEmpty() || edition.equals("0")) {
            redirectAttributes.addFlashAttribute("edition", empty);
        }

        String isbn = valueOf(book.getIsbn()).trim();
        if(isbn.isEmpty() || isbn.equals("null")) {
            redirectAttributes.addFlashAttribute("isbnn", empty);
        }

        String pages = valueOf(book.getPages()).trim();
        if(pages.isEmpty() || pages.equals("0")) {
            redirectAttributes.addFlashAttribute("pages", empty);
        }
    }

    private Book saveBook(Book book) {
        return bookRepository.save(book);
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
                        if(book.getStatus().equals(DELETED)){
                            result.put(isbn, 0);
                        }
                        else {
                            result.put(isbn, 1);
                        }
                    }
                    else if(!book.getStatus().equals(DELETED)){
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
