package com.ohgianni.tin.Controller;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Entity.Role;
import com.ohgianni.tin.Repository.RoleRepository;
import com.ohgianni.tin.Service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private ClientService clientService;
    private RoleRepository roleRepository;

    @Autowired
    public ClientController(ClientService clientService, RoleRepository roleRepository) {
        this.clientService = clientService;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/login", method = {GET, POST})
    public String viewLogin() {

        return "login";

    }

    @RequestMapping(value = "/profile", method = GET)
    public String login(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Client client = clientService.findClientByEmail(email);
        model.addAttribute("client", client);
        model.addAttribute("avatar", clientService.getAvatarFor(email));

        return "profile";

    }


    @RequestMapping(value = "/register", method = GET)
    public ModelAndView register(ModelAndView modelAndView) {

        return new ModelAndView("registration", "client", new ClientDTO());
    }

    @RequestMapping(value = "/register", method = POST)
    public ModelAndView register(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult result, ModelAndView mav, RedirectAttributes redirectAttributes) {

        roleRepository.save(new Role("CLIENT"));
        roleRepository.save(new Role("ADMIN"));

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
