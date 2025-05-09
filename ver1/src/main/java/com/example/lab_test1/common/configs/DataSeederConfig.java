package com.example.lab_test1.common.configs;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lab_test1.common.enums.UserRole;
import com.example.lab_test1.product.Product;
import com.example.lab_test1.product.ProductRepository;
import com.example.lab_test1.user.User;
import com.example.lab_test1.user.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Configuration
public class DataSeederConfig implements CommandLineRunner {
  private ProductRepository productRepository;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    seedProduct();
    seedUser();
  }

  public void seedUser() {
    var users = new ArrayList<User>();
    userRepository.deleteAll();

    users.add(User.builder()
        .email("admin@mail.com")
        .name("admin")
        .password(passwordEncoder.encode("123"))
        .role(UserRole.ADMIN.getName())
        .build());
    
    users.add(User.builder()
        .email("user@mail.com")
        .name("user")
        .password(passwordEncoder.encode("123"))
        .role(UserRole.USER.getName())
        .build());

    userRepository.saveAll(users);
  }

  public void seedProduct() {
    var products = new ArrayList<Product>();
    productRepository.deleteAll();

    products.add(Product.builder()
        .name("product 1")
        .quantity(12)
        .build());

    products.add(Product.builder()
        .name("product 2")
        .quantity(22)
        .build());

    products.add(Product.builder()
        .name("product 3")
        .quantity(1)
        .build());

    products.add(Product.builder()
        .name("product 4")
        .quantity(1)
        .build());

    products.add(Product.builder()
        .name("product 5j")
        .quantity(1)
        .build());

    products.add(Product.builder()
        .name("product 6")
        .quantity(1)
        .build());
    productRepository.saveAll(products);
  }
}
