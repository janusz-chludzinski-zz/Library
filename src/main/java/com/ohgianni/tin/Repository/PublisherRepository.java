package com.ohgianni.tin.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ohgianni.tin.Entity.Publisher;

@Repository
public interface PublisherRepository extends CrudRepository<Publisher, Long> {

    List<Publisher> findAll();

}
