package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestController {

    @RequestMapping("/test")
    public void test(Model model) {

        model.addAttribute("title", "TIN SITE");
        model.addAttribute("janusz", new Person("Janusz", "Chludziński", 31));
    }

}
