package com.sun.erpbackend.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sun.erpbackend.entity.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
