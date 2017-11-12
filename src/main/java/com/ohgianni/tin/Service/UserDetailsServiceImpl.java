package com.ohgianni.tin.Service;

import com.ohgianni.tin.Entity.Client;
import com.ohgianni.tin.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Client client = clientRepository.findByEmail(username);

        if(client == null) {
            throw new UsernameNotFoundException("Nie znaleźliśmy użytkownika o takim adresie email");
        }

        return new ClientDetailsService(client);

    }
}
