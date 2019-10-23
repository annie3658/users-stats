package com.library.user.stats.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class UserStatsApplication {


    @Bean
    public WebClient.Builder getWebClientBuilder(){
        return  WebClient.builder();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserStatsApplication.class, args);
    }

}
