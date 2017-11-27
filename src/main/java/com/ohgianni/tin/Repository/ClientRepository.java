package com.ohgianni.tin.Repository;

import com.ohgianni.tin.Entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{

    boolean existsByEmail(String email);

    Client findByEmail(String username);
}
