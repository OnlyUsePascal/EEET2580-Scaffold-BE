package com.example.test2.common.config;

import java.util.ArrayList;

import com.example.test2.common.enums.UserRole;
import com.example.test2.monitor.Monitor;
import com.example.test2.monitor.MonitorRepository;
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
  private MonitorRepository monitorRepository;
  private UserRepository userRepository;

  @Override
  public void run(String... args) {
    seedMonitor();
    seedUser();
  }

  public void seedUser() {
    var users = new ArrayList<User>();
    userRepository.deleteAll();

    users.add(User.builder()
            .email("admin@gmail.com")
            .name("admin")
            .password("123")
            .role(UserRole.ADMIN.getName())
            .build());

    users.add(User.builder()
            .email("user@gmail.com")
            .name("user")
            .password("123")
            .role(UserRole.USER.getName())
            .build());

    userRepository.saveAll(users);
  }

  public void seedMonitor() {
    var monitors = new ArrayList<Monitor>();
    monitorRepository.deleteAll();

    monitors.add(Monitor.builder()
            .name("Monitor 1")
            .brand("Brand 1")
            .price(100)
            .build());

    monitors.add(Monitor.builder()
            .name("Monitor 2")
            .brand("Brand 2")
            .price(200)
            .build());

    monitors.add(Monitor.builder()
            .name("Monitor 3")
            .brand("Brand 3")
            .price(300)
            .build());

    monitors.add(Monitor.builder()
            .name("Monitor 4")
            .brand("Brand 4")
            .price(400)
            .build());

    monitors.add(Monitor.builder()
            .name("Monitor 5")
            .brand("Brand 5")
            .price(500)
            .build());

    monitors.add(Monitor.builder()
            .name("Monitor 6")
            .brand("Brand 6")
            .price(600)
            .build());
    monitorRepository.saveAll(monitors);
  }
}
