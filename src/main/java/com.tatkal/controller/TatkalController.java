package com.tatkal.controller;

import com.tatkal.model.User;
import com.tatkal.service.Login.LoginService;
import com.tatkal.service.train.TrainDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TatkalController{

    @Autowired
    LoginService loginService;
    @Autowired
    TrainDetails trainDetails;

    @PostMapping("Login")
    public ResponseEntity<?> invokeLogin(@RequestBody User user){

        return ResponseEntity.ok(loginService.invokeLogin(user));
    }

    @GetMapping("getTrainById")
    public ResponseEntity<?> getTrainDetailsById(@RequestParam("TrainId") String trainId){
        return ResponseEntity.ok(trainDetails.trainDetails(trainId));
    }




}