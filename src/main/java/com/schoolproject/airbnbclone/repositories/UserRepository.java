package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>, CustomUserRepository {

    Optional<User> findByUsername(String username);

    @NonNull
    @Transactional
    @Query("SELECT u FROM User u WHERE u.username != 'admin'")
    Page<User> findAll(@NonNull Pageable pageable);

    @Transactional
    Boolean existsByUsername(String username);

    @Transactional
    Boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.hostApproved = ?2 WHERE u.username = ?1")
    Integer approveByUsername(String username, Boolean approvalStatus);

}
