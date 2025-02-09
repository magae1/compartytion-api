package com.compartytion.global.dto;


import org.springframework.web.server.ResponseStatusException;


public interface ResponseStatusExceptionBuilder {

  ResponseStatusException toResponseStatusException();

}
