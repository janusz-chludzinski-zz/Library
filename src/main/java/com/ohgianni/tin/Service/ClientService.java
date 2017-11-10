package com.ohgianni.tin.Service;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.transaction.Transactional;


@Service
public class ClientService {

    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Client saveClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO, passwordEncoder);
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
        if(!clientDTO.getPassword().equals(clientDTO.getPasswordRepeat())){
            result.addError(new FieldError("password", "password", "Podane hasła nie są identyczne"));
        }

    }


}
