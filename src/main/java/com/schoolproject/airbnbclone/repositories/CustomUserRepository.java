package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUserRepository {

    List<UserBasicDetails> findAllHostsForApproval();

}