package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT DISTINCT r FROM Role r where r.id in ?1")
    Set<Role> findAllDistinct(List<Integer> roles);
}
