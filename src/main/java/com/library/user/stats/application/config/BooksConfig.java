package com.library.user.stats.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("books")
public class BooksConfig {

    private String url;
    private String book;

    public BooksConfig() {
    }

    public BooksConfig(String url, String book) {
        this.url = url;
        this.book = book;
    }
}
