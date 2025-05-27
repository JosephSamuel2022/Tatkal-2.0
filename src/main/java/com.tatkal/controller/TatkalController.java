package com.tatkal.controller;

import com.tatkal.model.TrainAvailabilityDetails;
import com.tatkal.model.UserDAO;
import com.tatkal.service.Login.LoginService;
import com.tatkal.service.train.TrainDetails;
import com.tatkal.utils.ApiResponse;
import com.tatkal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tatkal.dto.TrainAvailabilityDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tatkal")
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

    @GetMapping("/getTrainAvailability")
    public ResponseEntity<?> getTrainAvailability(){
        return ResponseEntity.ok(trainDetails.getTrainAvailabilityStatus());
    }
    @PostMapping("/api/trains/bulk")
    public ResponseEntity<ApiResponse> addTrainAvailabilityBulk(@RequestBody List<TrainAvailabilityDetails> trains) {
        try {
            trainDetails.saveAll(trains);
            ApiResponse response = new ApiResponse(true, "Train availability data saved successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse response = new ApiResponse(false, "Failed to save train availability data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/getTrains")
    public List<TrainAvailabilityDTO> getAvailableTrains(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "general") String type) {

        return trainDetails.getTrainAvailability(source, destination, date, type);
    }
}