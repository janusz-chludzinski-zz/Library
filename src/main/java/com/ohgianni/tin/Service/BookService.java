package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private BookRepository bookRepository;

    private static final String BASE64_PREFIX = "data:image/jpeg;base64,";

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
         return bookRepository.save(book);
    }

    @Transactional
    public List<Book> getAllBooks() {

        List<Book> books = (List<Book>)bookRepository.findAll();
        books.forEach(this::encodeBookCover);

        return books;
    }

    private void encodeBookCover(Book book) {
         book.setImageUrl(BASE64_PREFIX + encodeBase64String(book.getCoverImage()));
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        book.ifPresent(this::encodeBookCover);
        return book.orElse(new Book());
    }
}
