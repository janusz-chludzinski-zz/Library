package com.ohgianni.tin.Entity;

import com.ohgianni.tin.DTO.ClientDTO;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.time.LocalDateTime.*;


@Entity
@Data
public class Client extends User {

    @Column
    private LocalDateTime creationDate;

    @Column
    private byte[] avatar;


    @Column
    @ManyToMany
    @JoinTable(
            name = "Client_Role",
            joinColumns = @JoinColumn(name = "ClientId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "RoleId", referencedColumnName = "roleId"))
    protected Collection<Role> roles;


//    @Column
//    private Reservation reservation;

    public Client(){}

    public Client (ClientDTO clientDTO, PasswordEncoder passwordEncoder) {
        name = clientDTO.getName();
        surname= clientDTO.getSurname();
        dateOfBirth = LocalDate.parse(clientDTO.getDateOfBirth());
        gender = clientDTO.getGender();
        email = clientDTO.getEmail();
        password = passwordEncoder.encode(clientDTO.getPassword());
        creationDate = now();
    }

}
