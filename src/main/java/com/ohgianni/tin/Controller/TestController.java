package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {

    private BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(BookService bookService) {
        this.bookService = bookService;
    }

//    @RequestMapping("/login")
//    public void test(Model model) {
//
//        logger.info("Here I am!");
////        Author author = new Author("Janusz", "Chludzinski", LocalDate.of(1986, 02, 19));
////        author = personService.savePerson(author);
////        Book book = new Book("Testowy book", author);
////
////        book = bookService.saveBook(book);
////        author.addBook(book);
////
////        personService.savePerson(author);
//
////        logger.info(book.toString());
//
//        model.addAttribute("title", "TIN SITE");
////        model.addAttribute("janusz", author);
////        model.addAttribute("book", book);
//    }

}
