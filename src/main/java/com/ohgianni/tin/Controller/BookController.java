package com.ohgianni.tin.Controller;

import com.ohgianni.tin.DTO.BookDTO;
import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Exception.BookNotFoundException;
import com.ohgianni.tin.Service.BookService;
import com.ohgianni.tin.Service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    private PublisherService publisherService;

    @Autowired
    public BookController(BookService bookService, PublisherService publisherService) {
        this.bookService = bookService;
        this.publisherService = publisherService;
    }

    @RequestMapping("/{isbn}")
    public String getBook(@PathVariable(name = "isbn") Long isbn, Model model) {

        model.addAttribute("book", bookService.getBookByIsbn(isbn));

        return "book";
    }

    @RequestMapping("/reservation/{isbn}")
    public String reservationView(@PathVariable(name = "isbn") Long isbn, Model model) {

        model.addAttribute("book", bookService.getBookByIsbn(isbn));

        return "reservation";
    }

    @RequestMapping("/reserve/{isbn}")
    public String reserve(@PathVariable(name = "isbn") Long isbn, Authentication authentication, RedirectAttributes redirectAttributes) {

        Reservation reservation = null;
                try{
                    bookService.reserveBook(isbn, authentication.getName());
                } catch(BookNotFoundException exception) {
                    redirectAttributes.addFlashAttribute("error", exception.getMessage());
                    return "redirect:/book/reservation/{isbn}";
                }

        redirectAttributes.addFlashAttribute("reservation", reservation);

        return "redirect:/user/profile/rentals";

    }

    @RequestMapping("/edit/{isbn}")
    public String edit(@PathVariable Long isbn,  Model model) {
        Publisher publisher = new Publisher();

        BookDTO bookDto = new BookDTO(
                bookService.getBookByIsbn(isbn),
                bookService.getAllBooksByIsbn(isbn),
                publisher,
                publisherService.findAll()
        );

        model.addAttribute("bookDto", bookDto);
        return "admin-book";
    }

}
