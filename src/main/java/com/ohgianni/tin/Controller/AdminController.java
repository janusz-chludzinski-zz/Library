package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ReservationService reservationService;

    @Autowired
    public AdminController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @RequestMapping("/profile")
    public String viewProfile() {
        return "profile";
    }


    @RequestMapping("/reservations")
    public String viewReservations(Model model) {
        model.addAttribute("reservations", reservationService.findAllReservations());
        return "admin-reservations";
    }

    @RequestMapping("/rent")
    public String rentBook() {
        return null;
    }
}
