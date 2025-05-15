package com.tatkal.service.train;

import com.tatkal.Repository.Train;
import com.tatkal.dao.TrainDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TrainDetails {

    @Autowired
    Train train;

    public ResponseEntity<?> getTrainDetailsById(String trainId){
            Optional<TrainDAO> result=train.findById(trainId);
            if (result.isPresent()){
                System.out.println(result);
                return ResponseEntity.ok(result.get());
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Train Details Found");
            }
    }

    public ResponseEntity<?> addTrainDetails(TrainDAO trainDAO){
        TrainDAO result=train.save(trainDAO);
        System.out.println(result);
        return ResponseEntity.ok(result.getTrainId());

    }
}
