package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping
    public String viewAllBooks(Model model) {

        model.addAttribute("books", bookService.getAllBooksDistinct());

        return "home";
    }

}
