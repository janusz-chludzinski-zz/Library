package com.ohgianni.tin.Entity;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Service.ImageService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;


@Entity
@Getter @Setter
public class Client extends User {

    @Column
    private LocalDateTime creationDate;

    @Column
    private String avatarUrl;

    @Column
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "Client_Role",
            joinColumns = @JoinColumn(name = "ClientId", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "RoleId", referencedColumnName = "roleId"))
    protected List<Role> roles;


    @Column
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

    public Client(){}

    public Client (ClientDTO clientDTO, PasswordEncoder passwordEncoder, ImageService imageService) {
        name = clientDTO.getName();
        surname= clientDTO.getSurname();
        dateOfBirth = LocalDate.parse(clientDTO.getDateOfBirth());
        gender = clientDTO.getGender();
        email = clientDTO.getEmail();
        password = passwordEncoder.encode(clientDTO.getPassword());
        avatarUrl = imageService.getAvatarUrl(clientDTO);
        creationDate = now();
    }

}
