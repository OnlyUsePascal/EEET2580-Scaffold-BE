package com.example.test2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.test2.auth.AuthRequestFilter;
import com.example.test2.auth.AuthService;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("email not found :("));
    return user;
  }

  public UserDTO getMyProfile() {
    // convert from spring user to entity user
    var user = (User) AuthService.getCurrentUser();
    return new UserDTO(user.getEmail(), user.getPassword(), user.getName());
  }
}
