package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Author;
import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Repository.AuthorRepository;
import com.ohgianni.tin.Repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static java.util.Objects.isNull;


@Service
public class AdminService {

    private AuthorRepository authorRepository;

    private PublisherRepository publisherRepository;

    @Autowired
    public AdminService(AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    public void addAuthor(Author author) {
        author.resolveDateOfBirth();
        authorRepository.save(author);
    }

    public void addPublisher(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    public void validatePublisher(Publisher publisher, RedirectAttributes redirectAttributes) {
        if(publisher.getName().trim().isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Nazwa wydawnictwa nie może być pusta");
        }
    }

    public void validateAuthor(Author author, BindingResult errors) {
        if(author.getName().trim().isEmpty()){
            errors.addError(new FieldError("name", "name", "Pole imię nie może być puste"));
        }

        if(author.getSurname().trim().isEmpty()) {
            errors.addError(new FieldError("surname", "surname", "Pole nazwisko nie może być puste"));
        }

        if(isNull(author.getGender())) {
            errors.addError(new FieldError("gender", "gender", "Proszę wybrać płeć"));
        }

        if(author.getDateOfBirthString().isEmpty()) {
            errors.addError(new FieldError("dateOfBirth", "dateOfBirth", "Proszę wybrać datę urodzenia"));
        }
    }
}
