package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.BookService;
import com.ohgianni.tin.Service.ClientService;
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

    private ClientService clientService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(BookService bookService, ClientService clientService) {
        this.bookService = bookService;
        this.clientService = clientService;
    }

    @RequestMapping("/test")
    public String test(Model model) {

        return null;

    }

}
