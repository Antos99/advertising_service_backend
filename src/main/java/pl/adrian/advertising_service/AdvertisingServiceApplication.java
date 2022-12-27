package pl.adrian.advertising_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import pl.adrian.advertising_service.config.DataGenerator;

@SpringBootApplication
public class AdvertisingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdvertisingServiceApplication.class, args);
    }

    @Bean
    @Profile("!test")
    CommandLineRunner run(DataGenerator dataGenerator){
        return args -> {dataGenerator.generateData();};
    }

}
