package com.tatkal.service.train;

import com.tatkal.Exception.CommonException;
import com.tatkal.Repository.Train;
import com.tatkal.Repository.TrainAvailabilityDetailsRepository;
import com.tatkal.dao.TrainDAO;
import com.tatkal.model.TrainAvailabilityDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrainDetails {

    private static final Logger log = LoggerFactory.getLogger(TrainDetails.class);
    @Autowired
    Train train;

    @Autowired
    TrainAvailabilityDetailsRepository trainDetails;

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

    public ResponseEntity<?> getTrainDetailsBySourceAndDestination(String source, String destination) throws Exception {
        try {
            List<TrainAvailabilityDetails> result = trainDetails.findMatchingRoutes(source, destination);
            log.info("Result" + result.toString());
            if (result.isEmpty()) {
                return new ResponseEntity<>(Collections.singletonMap("message", "No Train Details Found for the Source and Destination"), HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(result);
            }
        }
        catch (Exception e){
            throw new CommonException("Exception occurred While SQL Query",e);
        }
    }
}
