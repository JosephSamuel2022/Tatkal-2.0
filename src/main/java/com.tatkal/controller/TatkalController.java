package com.tatkal.controller;

import com.tatkal.model.UserDAO;
import com.tatkal.service.Login.LoginService;
import com.tatkal.service.train.TrainDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("External")
public class TatkalController{

    @Autowired
    LoginService loginService;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    TrainDetails trainDetails;

    @PostMapping("/login")
    public ResponseEntity<?> invokeLogin(@RequestBody UserDAO userDAO){

        boolean isAuthenticated = loginService.authenticate(userDAO.getUserName(), userDAO.getPassword());
        if(isAuthenticated) {
            String token = jwtUtil.generateToken(userDAO.getUserName());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login Successful");
            response.put("user", userDAO.getUserName());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Invalid username or password"));

    }

    @GetMapping("/getTrainById")
    public ResponseEntity<?> getTrainDetailsById(@RequestParam("TrainId") String trainId){
        return ResponseEntity.ok(trainDetails.getTrainDetailsById(trainId));
    }






}