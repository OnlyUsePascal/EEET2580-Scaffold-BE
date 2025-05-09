package com.example.test2.auth;

import com.example.test2.auth.dto.LoginDTO;
import com.example.test2.user.dto.UserDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
    public UserDTO getMyProfile();

    public Cookie signIn(LoginDTO dto) throws Exception;

    public Cookie signOut(@NotNull HttpServletRequest request);
}
