package com.ohgianni.tin.Controller;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/user")
public class ClientController {

    private ClientService clientService;

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping(value = "/login", method = GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = GET)
    public ModelAndView register(ModelAndView modelAndView) {

        return new ModelAndView("registration", "client", new ClientDTO());

    }

    @RequestMapping(value = "/register", method = POST)
    public ModelAndView register(@ModelAttribute("client") @Valid ClientDTO clientDTO, BindingResult result) {

        result = clientService.validateData(clientDTO, result);

        if(result.hasErrors()){
            return new ModelAndView("registration", "errors", result);
        }

        Client client = clientService.saveClient(clientDTO);
        return new ModelAndView("login", "client", client);
    }

    @RequestMapping("/reset-password")
    public String resetPassword() {
        return "reset-password";
    }
}
