package com.tatkal.controller;

import com.tatkal.Repository.Train;
import com.tatkal.dao.TrainDAO;
import com.tatkal.service.train.TrainDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/InternalUseOnly")
public class InternalDBEditsController {

    @Autowired
    TrainDetails trainDetails;

    @GetMapping("/addTrainDetails")
    public ResponseEntity<?> addTrainDetails(@RequestBody TrainDAO train){
        return trainDetails.addTrainDetails(train);
    }
}
