package com.tatkal.service.train;

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
import com.tatkal.dto.TrainAvailabilityDTO;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainDetails {

    private static final Logger log = LoggerFactory.getLogger(TrainDetails.class);
    @Autowired
    Train train;
    @Autowired
    TrainAvailabilityDetailsRepository trainAvailability;
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
    public ResponseEntity<?> getTrainAvailabilityStatus() {
        List<TrainAvailabilityDetails> result = trainAvailability.findAll();
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Train Details Found");
        } else {
            return ResponseEntity.ok(result);
        }
    }
    public void saveAll(List<TrainAvailabilityDetails> trainDetailsList) {
        trainAvailability.saveAll(trainDetailsList);
    }
    public List<com.tatkal.dto.TrainAvailabilityDTO> getTrainAvailability(String source, String destination, LocalDate date, String reservationType) {
        List<TrainAvailabilityDetails> trains = trainAvailability.findBySourceAndDestinationAndDate(source, destination, date);
        trains.sort(Comparator.comparing(TrainAvailabilityDetails::getTravelDate));

        return trains.stream().map(train -> {
            TrainAvailabilityDTO dto = new TrainAvailabilityDTO();
            dto.setTrainId(train.getTrainId());
            dto.setTrainName(train.getTrainName());
            dto.setSource(train.getSource());
            dto.setDestination(train.getDestination());
            dto.setTravelDate(train.getTravelDate());

            if ("tatkal".equalsIgnoreCase(reservationType)) {
                dto.setSL(train.getTotalTatkalSeatsSL());
                dto.setS2(train.getTotalTatkalSeats2S());
                dto.setCC(train.getTotalTatkalSeatsCC());
                dto.setA3(train.getTotalTatkalSeats3A());
                dto.setA2(train.getTotalTatkalSeats2A());
                dto.setA1(train.getTotalTatkalSeats1A());
            } else {
                dto.setSL(train.getTotalSeatsSL());
                dto.setS2(train.getTotalSeats2S());
                dto.setCC(train.getTotalSeatsCC());
                dto.setA3(train.getTotalSeats3A());
                dto.setA2(train.getTotalSeats2A());
                dto.setA1(train.getTotalSeats1A());
            }

            return dto;
        }).collect(Collectors.toList());
    }
}
