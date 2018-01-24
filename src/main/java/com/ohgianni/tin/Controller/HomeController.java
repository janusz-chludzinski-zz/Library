package com.ohgianni.tin.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ohgianni.tin.Service.BookService;
import com.ohgianni.tin.Service.RecommendationService;

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

    @RequestMapping("/recommendations/vote/{id}")
    public ModelAndView vote(@PathVariable(name = "id") Long id, ModelAndView modelAndView, HttpSession session, RedirectAttributes redirectAttributes) {
        recommendationService.addVote(id, session, redirectAttributes);
        modelAndView.setViewName("redirect:/recommendations");
        return modelAndView;
    }

}

