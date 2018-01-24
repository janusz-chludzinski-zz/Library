package com.ohgianni.tin.Entity;

import static java.time.LocalDateTime.now;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ohgianni.tin.DTO.ClientDTO;
import com.ohgianni.tin.Service.ImageService;
import lombok.Getter;
import lombok.Setter;


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

    @Column
    @OneToMany(mappedBy = "client")
    private List<Recommendation> recommendations;

    @Column
    @ManyToMany(mappedBy = "voters")
    private Set<Recommendation> votedRecommendations;

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

    public void addVotedRecommendation(Recommendation recommendation) {
        votedRecommendations.add(recommendation);
    }

    public boolean isClient() {
        return roles.stream().anyMatch(role -> role.getName().equals("CLIENT"));
    }
}
