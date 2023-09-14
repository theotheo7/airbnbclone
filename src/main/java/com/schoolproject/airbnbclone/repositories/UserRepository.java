package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @NonNull
    @Transactional
    @Query("SELECT u From User u WHERE u.username != 'admin'")
    Page<User> findAll(@NonNull Pageable pageable);

}
