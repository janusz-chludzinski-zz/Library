package com.ohgianni.tin.Repository;

import com.ohgianni.tin.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>{
}
