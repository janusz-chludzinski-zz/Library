package com.ohgianni.tin.Repository;

import java.util.List;

import com.ohgianni.tin.Entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();
}
