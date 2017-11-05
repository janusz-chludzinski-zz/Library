package com.ohgianni.tin.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {


//    @RequestMapping("/login-view")
//    public String login() {
//        return "login";
//    }

    @RequestMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }
}
