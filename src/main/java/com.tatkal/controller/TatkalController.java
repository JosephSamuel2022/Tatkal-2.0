package com.tatkal.controller;

import com.tatkal.model.User;
import com.tatkal.service.Login.LoginService;
import com.tatkal.service.train.TrainDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.JwtUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tatkal")
public class TatkalController{

    @Autowired
    LoginService loginService;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    TrainDetails trainDetails;

    @PostMapping("/login")
    public ResponseEntity<?> invokeLogin(@RequestBody User user){

        boolean isAuthenticated = loginService.authenticate(user.getUserName(), user.getPassword());
        if(isAuthenticated) {
            String token = jwtUtil.generateToken(user.getUserName());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "Login Successful");
            response.put("user", user.getUserName());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Collections.singletonMap("error", "Invalid username or password"));

    }

    @GetMapping("/getTrainById")
    public ResponseEntity<?> getTrainDetailsById(@RequestParam("TrainId") String trainId){
        return ResponseEntity.ok(trainDetails.trainDetails(trainId));
    }




}