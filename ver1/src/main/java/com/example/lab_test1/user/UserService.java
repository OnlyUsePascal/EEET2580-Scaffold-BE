package com.example.lab_test1.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
  public User loadUserByUsername(String email) throws UsernameNotFoundException;
}
