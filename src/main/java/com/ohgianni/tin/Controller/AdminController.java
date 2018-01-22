package com.ohgianni.tin.Controller;

import com.ohgianni.tin.DTO.BookDTO;
import com.ohgianni.tin.Entity.Author;
import com.ohgianni.tin.Entity.Publisher;
import com.ohgianni.tin.Exception.ReservationNotFoundException;
import com.ohgianni.tin.Service.AdminService;
import com.ohgianni.tin.Service.ClientService;
import com.ohgianni.tin.Service.PublisherService;
import com.ohgianni.tin.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ReservationService reservationService;

    private ClientService clientService;

    private AdminService adminService;

    private PublisherService publisherService;

    @Autowired
    public AdminController(ReservationService reservationService, ClientService clientService,
                           AdminService adminService, PublisherService publisherService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.adminService = adminService;
        this.publisherService = publisherService;
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

    @RequestMapping("/clients")
    public String displayClients(Model model) {
        model.addAttribute("clients", clientService.findAll());
        return "admin-clients";
    }

    @RequestMapping("/add/author")
    public String addAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "admin-add-author";
    }

    @RequestMapping("/add/book")
    public String addBook(Model model) {
        BookDTO bookDto = new BookDTO();
        model.addAttribute("bookDto", bookDto);

        return "admin-add-book";
    }

    @RequestMapping(value = "/add/author", method = POST)
    public String addAuthor(@ModelAttribute("author") @Valid Author author,
                            BindingResult result,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        adminService.validateAuthor(author, result);

        if(result.hasErrors()) {
            model.addAttribute("errors", result);
            return "admin-add-author";
        }
        adminService.addAuthor(author);
        redirectAttributes.addFlashAttribute("success", "Autor " + author.getName() + " " + author.getSurname() + " został pomyślnie dodany!");

        return "redirect:/admin/add/author";
    }

    @RequestMapping(value = "/add/publisher")
    public String addPublisher(Model model) {
        model.addAttribute("publisher", new Publisher());
        return "admin-add-publisher";
    }

    @RequestMapping(value = "/add/publisher", method = POST)
    public String addPublisher(@ModelAttribute("author") @Valid Publisher publisher,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        adminService.validatePublisher(publisher, redirectAttributes);

        if(redirectAttributes.getFlashAttributes().containsKey("error")) {
            return "redirect:/admin/add/publisher";
        }
        adminService.addPublisher(publisher);
        redirectAttributes.addFlashAttribute("success", "Wydawnictwo " + publisher.getName() + " zostało pomyślnie dodane!");

        return "redirect:/admin/add/publisher";
    }
}
