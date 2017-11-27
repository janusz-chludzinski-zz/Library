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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

import static com.ohgianni.tin.Enum.Gender.MALE;
import static java.util.Arrays.asList;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64String;


@Service
public class ClientService {

    private static final String FEMALE_AVATAR_URL = "/img/female.png";

    private static final String MALE_AVATAR_URL = "/img/male.png";

    private static final String BASE64_PREFIX = "data:image/jpeg;base64,";

    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Client saveClient(ClientDTO clientDTO) {
        Client client = new Client(clientDTO, passwordEncoder);
        assignRole(client);
        convertAvatar(clientDTO.getAvatar(), client);
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
        client.setRoles(roleRepository.findAllById(asList(1L)));
    }

    private void convertAvatar(MultipartFile file, Client client) {
        try {
            client.setAvatar(file.getBytes());
        } catch (IOException e) {
            client.setAvatar(new byte[0]);
        }
    }

    @Transactional
    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public String getAvatarFor(String email) {
        String result = null;
        Client client = clientRepository.findByEmail(email);

        byte[] avatar = client.getAvatar();

        if (avatar.length == 0) {

            result = client.getGender() == MALE ? MALE_AVATAR_URL : FEMALE_AVATAR_URL;

        } else {

            result = BASE64_PREFIX + encodeBase64String(clientRepository.findByEmail(email).getAvatar());

        }

        return result;
    }
}
