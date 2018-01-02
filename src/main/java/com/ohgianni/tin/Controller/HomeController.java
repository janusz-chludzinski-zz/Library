package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.BookService;
import com.ohgianni.tin.Service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    private final BookService bookService;

    private final RecommendationService recommendationService;

    @Autowired
    public HomeController(BookService bookService, RecommendationService recommendationService) {
        this.bookService = bookService;
        this.recommendationService = recommendationService;
    }

    @RequestMapping("/home")
    public ModelAndView viewAllBooks(ModelAndView modelAndView) {

        modelAndView.setViewName("home");
        modelAndView.addObject("books", bookService.getAllBooksDistinct());

        return modelAndView;
    }

    @RequestMapping("/recommendations")
    public ModelAndView viewRecommendationsRank(ModelAndView modelAndView) {

        modelAndView.setViewName("recommendations");
        modelAndView.addObject("recommendations", recommendationService.findAll());

        return modelAndView;
    }

}
