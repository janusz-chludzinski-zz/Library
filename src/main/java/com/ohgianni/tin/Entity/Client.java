package com.ohgianni.tin.Entity;

import com.ohgianni.tin.DTO.ClientDTO;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;


@Entity
@Data
public class Client extends User {

    @Column
    private LocalDateTime creationDate;

//    @Column
//    private Reservation reservation;

    public Client (ClientDTO clientDTO, PasswordEncoder passwordEncoder) {
        name = clientDTO.getName();
        surname= clientDTO.getSurname();
        dateOfBirth = clientDTO.getDateOfBirth();
        gender = clientDTO.getGender();
        email = clientDTO.getEmail();
        password = passwordEncoder.encode(clientDTO.getPassword());
        creationDate = now();
    }

}
