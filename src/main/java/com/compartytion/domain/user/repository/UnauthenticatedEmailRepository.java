package com.compartytion.domain.user.repository;


import org.springframework.data.repository.CrudRepository;

import com.compartytion.domain.model.UnauthenticatedEmail;


public interface UnauthenticatedEmailRepository extends
    CrudRepository<UnauthenticatedEmail, String> {

}
