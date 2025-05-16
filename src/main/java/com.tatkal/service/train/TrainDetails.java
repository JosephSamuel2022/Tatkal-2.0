package com.tatkal.service.train;

import com.tatkal.Repository.Train;
import com.tatkal.dao.TrainDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TrainDetails {

    private static final Logger log = LoggerFactory.getLogger(TrainDetails.class);
    @Autowired
    Train train;

    public ResponseEntity<?> getTrainDetailsById(String trainId){
            Optional<TrainDAO> result=train.findById(trainId);
            if (result.isPresent()){
                log.info(result.get().toString());
                TrainDAO trainDetail= result.get();
                return ResponseEntity.ok(trainDetail);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Train Details Found");
            }
    }

    public ResponseEntity<?> addTrainDetails(List<TrainDAO> trainDAO){
        List<TrainDAO> result=train.saveAll(trainDAO);
        return ResponseEntity.status(200).body(result);

    }

    public ResponseEntity<?> deleteTrainDetailsById(String trainId) {
        Optional<TrainDAO> result=train.findById(trainId);
        if (result.isPresent()){
            train.deleteById(trainId);
            return ResponseEntity.ok(200);
        }
        else{
            return ResponseEntity.status(404).body("Train Id Not Found");
        }
    }
    public ResponseEntity<?> getTrainDetails(){
        List<TrainDAO> result=train.findAll();
        if (result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Train Details Found");
        }
        else{
            return ResponseEntity.status(200).body(result);
        }
    }
}
