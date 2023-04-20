package io.guatimosim1.config;

import io.guatimosim1.model.entity.User;
import io.guatimosim1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {
    @Autowired
    private UserRepository repository;
    @Bean
    public void startDataBase() {
        User u1 = new User().builder()
                .name("Naruto")
                .email("naruto@email.com")
                .password("123")
                .build();
        User u2 = new User().builder()
                .name("Sasuke")
                .email("sasuke@email.com")
                .password("123")
                .build();
        repository.saveAll(List.of(u1, u2));
    }
}
