package com.ohgianni.tin.Controller;

import com.ohgianni.tin.Exception.ReservationNotFoundException;
import com.ohgianni.tin.Service.ClientService;
import com.ohgianni.tin.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ReservationService reservationService;

    private ClientService clientService;

    @Autowired
    public AdminController(ReservationService reservationService, ClientService clientService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
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

    @RequestMapping("/rent/{id}")
    public String rentBook(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {

        try {

            redirectAttributes.addFlashAttribute("reservation", reservationService.rentBook(id));

        } catch (ReservationNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", e.getMessage());

        }

        return "redirect:/admin/rentals";

    }

    @RequestMapping("/rentals")
    public String showRentals(Model model) {
        model.addAttribute("rentals", reservationService.findAllRentals());

        return "admin-rentals";
    }

    @RequestMapping("/return/{rentId}")
    public String returnBook(@PathVariable(name = "rentId") Long rentId, RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("reservation", reservationService.returnBook(rentId));
        } catch (ReservationNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/rentals";
    }

    @RequestMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        reservationService.cancelReservation(id, redirectAttributes);
        return "redirect:/admin/reservations";
    }
}
