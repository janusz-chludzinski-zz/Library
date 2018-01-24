package com.ohgianni.tin.Service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Repository.ClientRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private ClientRepository clientRepository;

    private HttpSession session;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository, HttpSession session) {
        this.clientRepository = clientRepository;
        this.session = session;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Client client = clientRepository.findByEmail(username);

        if(client == null) {
            throw new UsernameNotFoundException("Nie znaleźliśmy użytkownika o takim adresie email");
        }

        session.setAttribute("client", client);

        return new ClientDetailsService(client);

    }
}
