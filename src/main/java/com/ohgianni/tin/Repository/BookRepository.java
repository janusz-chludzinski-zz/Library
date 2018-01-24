package com.ohgianni.tin.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Enum.BookStatus;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByIsbnAndStatus(Long aLong, BookStatus status);

    List<Book> findAllByIsbn(Long isbn);

    boolean existsByIsbn(Long isbn);



}
