package com.example.lab_test1.user;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userRepository.findByEmail(email);
    if (user.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found with email:" + email);
    return user.get();   
  }
}
