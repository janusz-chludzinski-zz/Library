package com.ohgianni.tin.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ohgianni.tin.Entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{

    Client findByEmail(String username);

    List<Client> findAllByOrderBySurnameDesc();

}
