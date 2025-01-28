package com.compartytion;

import org.springframework.boot.SpringApplication;

public class TestCompartytionApiApplication {

  public static void main(String[] args) {
    SpringApplication.from(CompartytionApplication::main).with(TestcontainersConfiguration.class)
        .run(args);
  }

}
