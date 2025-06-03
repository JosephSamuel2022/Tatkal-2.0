package com.tatkal.controller;

import com.tatkal.dao.PaymentDAO;
import com.tatkal.model.StripeResponse;
import com.tatkal.model.TrainAvailabilityDetails;
import com.tatkal.model.UserDAO;
import com.tatkal.service.Login.LoginService;
import com.tatkal.service.Payment.StripeService;
import com.tatkal.service.train.TrainDetails;
import com.tatkal.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    @Autowired
    StripeService stripeService;

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

    @PostMapping("/payment")
    public ResponseEntity<StripeResponse> makePayment(@RequestBody PaymentDAO paymentRequest) {
        StripeResponse response = stripeService.doPayment(paymentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getPaymentStatus")
    public String getPaymentStatus(@RequestParam("sessionId") String sessionId) {
        return stripeService.getPaymentStatus(sessionId);
    }

    @GetMapping("/getTrainDetailsBySourceAndDestination")
    public ResponseEntity<?> getTrainDetailsBySourceAndDestination(@RequestParam String Source, @RequestParam String Destination,@RequestParam LocalDate Date) throws Exception{
        return trainDetails.getTrainDetailsBySourceAndDestination(Source,Destination,Date);
    }


}