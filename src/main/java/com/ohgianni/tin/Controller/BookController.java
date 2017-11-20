package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/{id}")
    public String getBook(@PathVariable(name = "id") Long id, Model model) {

        model.addAttribute("book", bookService.getBookById(id));

        return "book";
    }

}
