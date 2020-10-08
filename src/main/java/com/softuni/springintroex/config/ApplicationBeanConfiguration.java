package com.softuni.springintroex.config;

import com.softuni.springintroex.io.ConsoleReader;
import com.softuni.springintroex.io.ConsoleWriter;
import com.softuni.springintroex.io.interfaces.InputReader;
import com.softuni.springintroex.io.interfaces.OutputWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public InputReader reader() {
        return new ConsoleReader();
    }

    @Bean
    public OutputWriter writer() {
        return new ConsoleWriter();
    }
}
