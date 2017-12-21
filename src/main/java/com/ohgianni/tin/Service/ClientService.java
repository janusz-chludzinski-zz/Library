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

import javax.transaction.Transactional;

import static java.util.Collections.singletonList;


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

    public BindingResult validateData(ClientDTO clientDTO, BindingResult errors) {
        checkIfEmailExists(clientDTO, errors);
        checkIfPasswordsMatch(clientDTO, errors);
        return errors;
    }

    private void checkIfEmailExists(ClientDTO clientDTO, BindingResult result) {
        if (clientRepository.existsByEmail(clientDTO.getEmail())) {
            result.addError(new FieldError("email", "email", "Ten email istnieje już w naszej bazie"));
        }
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



}
