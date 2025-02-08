package com.compartytion.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.compartytion.global.component.Snowflake;


@Configuration
public class SnowflakeConfig {

  @Bean
  public Snowflake snowflake() {
    return new Snowflake();
  }

}
