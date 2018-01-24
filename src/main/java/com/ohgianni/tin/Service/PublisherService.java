package com.ohgianni.tin.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Repository.PublisherRepository;


@Service
public class PublisherService {

    private static PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        PublisherService.publisherRepository = publisherRepository;
    }

    public static List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

}
