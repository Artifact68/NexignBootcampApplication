package org.example.nexignbootcampapplication;


import org.example.nexignbootcampapplication.service.CdrGenerationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Главный класс приложения.
 */
@SpringBootApplication
public class NexignBootcampApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexignBootcampApplication.class, args);
    }

    /**
     * При старте приложения генерируем CDR записи.
     */
    @Bean
    CommandLineRunner init(CdrGenerationService cdrGenerationService) {
        return args -> cdrGenerationService.generateCdrRecords();
    }
}
