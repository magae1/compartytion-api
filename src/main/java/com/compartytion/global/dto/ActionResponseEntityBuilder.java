package com.compartytion.global.dto;


import org.springframework.http.ResponseEntity;


public interface ActionResponseEntityBuilder {

  ResponseEntity<ActionResponse> toActionResponseEntity();

}
