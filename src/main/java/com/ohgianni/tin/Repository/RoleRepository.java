package com.ohgianni.tin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohgianni.tin.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
