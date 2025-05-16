package com.tatkal.controller;

import com.tatkal.Repository.Train;
import com.tatkal.dao.TrainDAO;
import com.tatkal.service.train.TrainDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("InternalUseOnly")
public class InternalDBEditsController {

    @Autowired
    TrainDetails trainDetails;

    @PostMapping("/addTrainDetails")
    public ResponseEntity<?> addTrainDetails(@RequestBody List<TrainDAO> train){
        return trainDetails.addTrainDetails(train);
    }

    @GetMapping("/getTrainDetails")
    public ResponseEntity<?> getTrainDetails(){
        return trainDetails.getTrainDetails();
    }

    @DeleteMapping("/deleteTrainDetailsById")
    public ResponseEntity<?> deleteTrainDetailsById(@RequestParam("trainId") String trainId){
        return trainDetails.deleteTrainDetailsById(trainId);
    }
}
