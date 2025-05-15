package com.tatkal.service.Login;

import org.springframework.stereotype.Service;

@Service
public class LoginService{

    public boolean authenticate(String username, String password) {
        return "user".equals(username) && "pass".equals(password);
    } //Have to replace with DB call
}