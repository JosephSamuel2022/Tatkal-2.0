package com.tatkal.service.Login;

import com.tatkal.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService{

    public boolean authenticate(String username, String password) {
        return "user".equals(username) && "pass".equals(password);
    } //Have to replace with DB call
}