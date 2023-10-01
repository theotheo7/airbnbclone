package com.schoolproject.airbnbclone.repositories;

import com.schoolproject.airbnbclone.dtos.user.response.UserBasicDetails;
import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<UserBasicDetails> findAllHostsForApproval() {
        TypedQuery<UserBasicDetails> q = entityManager.createNamedQuery("userQry", UserBasicDetails.class);
        return q.getResultList();
    }

}
