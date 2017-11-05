package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Entity.Book;
import com.ohgianni.tin.Entity.Person;
import com.ohgianni.tin.Service.BookService;
import com.ohgianni.tin.Service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.logging.Level;

@Controller
@RequestMapping("/")
public class TestController {

    private BookService bookService;

    private PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @RequestMapping("/login")
    public void test(Model model) {

        logger.info("Here I am!");
        Person person = new Person("Janusz", "Chludzinski", LocalDate.of(1986, 02, 19));
        person = personService.savePerson(person);
        Book book = new Book("Testowy book", person);

        book = bookService.saveBook(book);
        person.addBook(book);

        personService.savePerson(person);

//        logger.info(book.toString());

        model.addAttribute("title", "TIN SITE");
        model.addAttribute("janusz", person);
        model.addAttribute("book", book);
    }

}
