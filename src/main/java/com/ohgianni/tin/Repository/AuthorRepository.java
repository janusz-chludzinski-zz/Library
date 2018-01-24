package com.ohgianni.tin.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ohgianni.tin.Entity.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findAll();
}
