package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    public Publisher findById(Long id){
        return publisherRepository.findById(id).orElse(new Publisher());
    }
}
