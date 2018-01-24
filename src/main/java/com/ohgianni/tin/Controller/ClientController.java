package com.ohgianni.tin.Controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Recommendation;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Service.ClientService;
import com.ohgianni.tin.Service.RecommendationService;
import com.ohgianni.tin.Service.ReservationService;

@Controller
@RequestMapping("/user")
public class ClientController {

    private ReservationService reservationService;

    private ClientService clientService;

    private RecommendationService recommendationService;

    @Autowired
    public ClientController(ClientService clientService, ReservationService reservationService, RecommendationService recommendationService) {
        this.clientService = clientService;
        this.reservationService = reservationService;
        this.recommendationService = recommendationService;
    }

    @RequestMapping(value = "/login", method = GET)
    public String viewLogin() {

        return "login";

    }

    @RequestMapping(value = "/login", method = POST)
    public String doLogin(Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());

        return clientService.isAdmin(client) ? "redirect:/admin/reservations" : "profile";
    }

    @RequestMapping(value = "/profile", method = GET)
    public String login(HttpSession session, Authentication authentication, Model model) {
        Client client = clientService.findClientByEmail(authentication.getName());
        session.setAttribute("client", client);

        model.addAttribute("client", new ClientDTO(client));

        return clientService.isAdmin(client) ? "redirect:/admin/reservations" : "profile";

    }

    @RequestMapping(value = "/profile", method = POST, produces = "image/jpg")
    public ModelAndView updateProfile(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                                      BindingResult errors,
                                      ModelAndView modelAndView,
                                      HttpSession session) {

        errors = clientService.validateData(clientDTO, session, errors);
        modelAndView.setViewName("profile");

        if(errors.hasErrors()) {
            modelAndView.addObject("errors", errors);

            return modelAndView;
        }

        modelAndView.addObject("client", new ClientDTO(clientService.updateClient(clientDTO, session)));
        modelAndView.addObject("message", "Dane zostały pomyślnie zmienione");

        return modelAndView;
    }

    @RequestMapping("/profile/rentals")
    public String showRentals(Authentication authentication, Model model) {
        List<Reservation> reservations =  reservationService.getAllReservationsForClient(clientService.findClientByEmail(authentication.getName()));
        model.addAttribute("reservations", reservations);

        return "profile-reservations";
    }

    @RequestMapping(value = "/profile/recommendation", method = GET)
    public ModelAndView viewRecommendationForm() {
        return new ModelAndView("recommendation", "recommendation", new Recommendation());
    }

    @RequestMapping(value = "/profile/recommendation", method = POST)
    public ModelAndView postRecommendation(@ModelAttribute("recommendation") @Valid Recommendation recommendation,
                                           BindingResult errors,
                                           ModelAndView modelAndView) {

        modelAndView.setViewName("recommendation");
        errors = recommendationService.validateRecommendation(recommendation, errors);

        if(errors.hasErrors()) {
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }

        Recommendation savedRecommendation = recommendationService.addRecommendation(recommendation);

        modelAndView.addObject("message", "Rekomendacja książki o numerze ISBN " + savedRecommendation.getIsbn() + " została pomyślnie dodana, dziękujemy!");
        modelAndView.addObject("recommendation", new Recommendation());

        return modelAndView;
    }


    @RequestMapping(value = "/register", method = GET)
    public ModelAndView register() {
        return new ModelAndView("registration", "client", new ClientDTO());
    }

    @RequestMapping(value = "/register", method = POST)
    public ModelAndView register(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                                 BindingResult errors,
                                 ModelAndView modelAndView,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {

        errors = clientService.validateData(clientDTO, session, errors);

        if (errors.hasErrors()) {
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("registration");

            return modelAndView;
        }

        clientService.saveClient(clientDTO);

        redirectAttributes.addFlashAttribute("message", "Dziękujemy za rejestrację. Możesz się teraz zalogować");
        modelAndView.setViewName("redirect:/user/login");

        return modelAndView;
    }

    @RequestMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }
}
