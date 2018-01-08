package com.ohgianni.tin.DTO;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Enum.Gender;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.file.Paths;

@Data
public class ClientDTO {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty (message = "Pole nie może być puste")
    private String surname;

    @NotNull
    @NotEmpty (message = "Pole nie może być puste")
    private String dateOfBirth;

    @NotNull (message = "Proszę wybrać płeć")
    private Gender gender;

    @NotNull
    @NotEmpty (message = "Pole nie może być puste")
    @Email (message = "Zły format adresu e-mail")
    private String email;

    @NotNull
    @NotEmpty (message = "Pole nie może być puste")
    private String password;

    @NotNull
    @NotEmpty (message = "Pole nie może być puste")
    private String passwordRepeat;

    private MultipartFile avatar;

    public ClientDTO(){}

    public ClientDTO(Client client) {
        this.name = client.getName();
        this.surname = client.getSurname();
        this.dateOfBirth = client.getDateOfBirth().toString();
        this.gender = client.getGender();
        this.email = client.getEmail();
    }
}
