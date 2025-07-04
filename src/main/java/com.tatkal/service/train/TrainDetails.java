package com.tatkal.service.train;

import com.tatkal.Exception.CommonException;
import com.tatkal.Repository.Train;
import com.tatkal.Repository.TrainAvailabilityDetailsRepository;
import com.tatkal.Repository.TrainCoachDetailsRepository;
import com.tatkal.dao.TrainCoachDetails;
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

    @Autowired
    TrainAvailabilityDetailsRepository trainDetails;

    @Autowired
    TrainCoachDetailsRepository trainCoachDetails;

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

    public ResponseEntity<?> getTrainDetailsBySourceAndDestination(String source, String destination, LocalDate Date) throws Exception {
        try {
            List<TrainAvailabilityDetails> result = trainDetails.findMatchingRoutes(source, destination,Date);
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
        trains.sort(Comparator.comparing(TrainAvailabilityDetails::getDate));

        return trains.stream().map(train -> {
            TrainAvailabilityDTO dto = new TrainAvailabilityDTO();
            dto.setTrainId(train.getTrainId());
            dto.setTrainName(train.getTrainName());
            dto.setSource(train.getSource());
            dto.setDestination(train.getDestination());
            dto.setTravelDate(train.getDate());

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
    public TrainAvailabilityDetails getTrainById(String trainId, LocalDate date){
        return trainAvailability.findByTrainIdAndDate(trainId,date);
    }

    public Map<String, List<Integer>> getSeatsList(String trainId, LocalDate journeyDate, String coachType) {
        TrainCoachDetails details = trainCoachDetails
          .findByTrainIdAndJourneyDateAndCoachType(trainId, journeyDate, coachType)
          .orElseThrow(() -> new RuntimeException("Train coach details not found!"));

        Map<String, List<Integer>> seatsMap = new HashMap<>();
        seatsMap.put("upper", new ArrayList<>(details.getUpperBalanceSeatNo()));
        seatsMap.put("lower", new ArrayList<>(details.getLowerBalanceSeatNo()));
        seatsMap.put("middle", new ArrayList<>(details.getMiddleBalanceSeatNo()));

        return seatsMap;
    }
}
