package com.example.test2.common.config;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.test2.user.User;
import com.example.test2.user.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Configuration
public class DataSeederConfig implements CommandLineRunner {
  private UserRepository userRepository;

  @Override
  public void run(String... args) {
    var users = new ArrayList<User>();
    users.add(User.builder()
    .email("admin@gmail.com")
    .password("123")
    .role("ADMIN")
    .name("admin user")
    .build());
    
    users.add(User.builder()
    .email("user@gmail.com")
    .password("123")
    .role("USER")
    .name("user user")
    .build());

    userRepository.deleteAll();
    userRepository.saveAll(users);
  }
}
