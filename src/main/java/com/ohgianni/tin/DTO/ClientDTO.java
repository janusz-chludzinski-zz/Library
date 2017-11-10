package com.ohgianni.tin.DTO;

import com.ohgianni.tin.Enum.Gender;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.FieldError;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

@Data
public class ClientDTO {

    @NotNull
    @NotEmpty (message = "Pole nie może być puste")
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

}
