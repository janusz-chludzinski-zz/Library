package com.ohgianni.tin.Service;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Repository.ClientRepository;
import com.ohgianni.tin.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;


@Service
public class ClientService {

    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private ImageService imageService;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ImageService imageService) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageService = imageService;
    }

    @Transactional
    public Client saveClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO, passwordEncoder, imageService);
        assignRole(client);
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(ClientDTO clientDTO, HttpSession session) {
        Client client = new Client(clientDTO, passwordEncoder, imageService);
        client.setId(clientRepository.findByEmail(clientDTO.getEmail()).getId());

        assignRole(client);
        client =  clientRepository.save(client);
        updateSession(client, session);

        return client;
    }

    private void updateSession(Client client, HttpSession session) {
        session.setAttribute("client", client);
    }

    public BindingResult validateData(ClientDTO clientDTO, HttpSession session, BindingResult errors) {
        checkIfEmailExists(clientDTO, session, errors);
        checkIfPasswordsMatch(clientDTO, errors);
        return errors;
    }

    private void checkIfEmailExists(ClientDTO clientDTO, HttpSession session, BindingResult result) {
        Optional<Client> client = ofNullable(clientRepository.findByEmail(clientDTO.getEmail()));
        Optional<Client> loggedInClient = ofNullable((Client)session.getAttribute("client"));

        if (client.isPresent() && loggedInClient.isPresent()) {
            if(isSameClient(client.get(), loggedInClient.get())) {
               return;
            }

        } else if (client.isPresent()) {
            result.addError(new FieldError("email", "email", "Ten email istnieje już w naszej bazie"));
        }
    }

    private boolean isSameClient(Client client, Client loggedInClient) {
        return client.getId().equals(loggedInClient.getId());
    }

    private void checkIfPasswordsMatch(ClientDTO clientDTO, BindingResult result) {
        if (!clientDTO.getPassword().equals(clientDTO.getPasswordRepeat())) {
            result.addError(new FieldError("password", "password", "Podane hasła nie są identyczne"));
        }
    }

    private void assignRole(Client client) {
        if (client.getEmail().equals("chludzinski.janusz@gmail.com")) {
            client.setRoles(roleRepository.findAllById(singletonList(2L)));
        } else {
            client.setRoles(roleRepository.findAllById(singletonList(1L)));
        }
    }

    @Transactional
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public boolean isAdmin(Client client) {
        return client.getRoles().get(0).getRoleId() == 2L;
    }

    public List<Client> findAll() {
       return clientRepository.findAllByOrderBySurnameDesc();
    }
}
