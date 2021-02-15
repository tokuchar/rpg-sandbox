package com.oncors.rpg.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RandomConfig {
    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
