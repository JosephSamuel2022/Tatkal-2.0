package com.tatkal.service.Login;

import com.tatkal.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoginService{

    public String invokeLogin(User user){
        return user.getUserName();
    }
}