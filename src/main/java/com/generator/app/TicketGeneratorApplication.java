package com.generator.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.LinkedList;

@EnableTransactionManagement
@EnableJpaAuditing
@SpringBootApplication
public class TicketGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketGeneratorApplication.class, args);
    }
}
