package com.ohgianni.tin.Repository;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Enum.BookStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByIsbnAndStatus(Long aLong, BookStatus status);

    List<Book> findByIsbn(Long isbn);

    boolean existsByIsbn(Long isbn);



}
