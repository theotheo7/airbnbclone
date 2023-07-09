package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

}
