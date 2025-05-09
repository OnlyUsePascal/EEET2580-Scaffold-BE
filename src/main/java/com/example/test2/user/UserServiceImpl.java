package com.example.test2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    var user = userRepository.findByEmail(email);
    if (user.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with email: " + email);
    return user.get();
  }
}
