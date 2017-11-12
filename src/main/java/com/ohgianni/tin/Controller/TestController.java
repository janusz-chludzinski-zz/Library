package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TestController {

    private BookService bookService;

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/test")
    public ModelAndView test(ModelAndView model) {

        model.setViewName("test");

        return model;
    }

}
