package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MessageRepository extends JpaRepository<Message, Long>, PagingAndSortingRepository<Message, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.read = true WHERE m.id = :id")
    void readMessage(@Param("id") Long id);

    @Query("SELECT m FROM Message m JOIN FETCH m.sender sender JOIN FETCH m.recipient recipient WHERE sender.username = :username")
    Page<Message> getSentMessages(@Param("username") String username, Pageable pageable);

    @Query("SELECT m FROM Message m JOIN FETCH m.recipient recipient JOIN FETCH m.sender sender WHERE recipient.username = :username")
    Page<Message> getReceivedMessages(@Param("username") String username, Pageable pageable);

    @Query("SELECT m.recipient.username FROM Message m WHERE m.id = :id")
    String findRecipientById(@Param("id") Long id);
}
