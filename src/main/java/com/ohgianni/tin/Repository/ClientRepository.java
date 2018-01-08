package com.ohgianni.tin.Repository;

import com.ohgianni.tin.Entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{

    Client findByEmail(String username);

    List<Client> findAllByOrderBySurnameDesc();
}
