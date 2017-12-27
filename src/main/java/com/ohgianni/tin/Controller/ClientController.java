package com.ohgianni.tin.Controller;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Reservation;
import com.ohgianni.tin.Service.ClientService;
import com.ohgianni.tin.Service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class ClientController {

    private ReservationService reservationService;

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService, ReservationService reservationService) {
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/login", method = GET)
    public String viewLogin() {

        return "login";

    }

    @RequestMapping(value = "/profile", method = GET)
    public String login(HttpSession session, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());
        session.setAttribute("client", client);

        return clientService.isAdmin(client) ? "redirect:/admin/reservations" : "profile";

    }

    @RequestMapping(value = "/login", method = POST)
    public String doLogin(HttpSession session, Authentication authentication) {
        Client client = clientService.findClientByEmail(authentication.getName());

        return clientService.isAdmin(client) ? "redirect:/admin/reservations" : "profile";
    }

    @RequestMapping("/profile/rentals")
    public String showRentals(Authentication authentication, Model model) {
        List<Reservation> reservations =  reservationService.getAllReservationsForClient(clientService.findClientByEmail(authentication.getName()));
        model.addAttribute("reservations", reservations);

        return "profile-reservations";
    }


    @RequestMapping(value = "/register", method = GET)
    public ModelAndView register(ModelAndView modelAndView) {

        return new ModelAndView("registration", "client", new ClientDTO());
    }

    @RequestMapping(value = "/register", method = POST)
    public ModelAndView register(@ModelAttribute("client") @Valid ClientDTO clientDTO,
                                 BindingResult result,
                                 ModelAndView mav,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session) {

        result = clientService.validateData(clientDTO, result);

        if (result.hasErrors()) {
            mav.addObject("errors", result);
            mav.setViewName("registration");

            return mav;
        }

        clientService.saveClient(clientDTO);

        redirectAttributes.addFlashAttribute("message", "Dziękujemy za rejestrację. Możesz się teraz zalogować");
        mav.setViewName("redirect:/user/login");

        return mav;
    }

    @RequestMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }
}
