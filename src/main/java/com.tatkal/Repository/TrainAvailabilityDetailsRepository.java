package com.tatkal.Repository;

import com.tatkal.model.TrainAvailabilityDetails;
import com.tatkal.model.TrainCoachDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainAvailabilityDetailsRepository extends JpaRepository<TrainAvailabilityDetails, TrainCoachDetailsId> {
    List<TrainAvailabilityDetails> findBySourceAndDestinationAndDate(String source, String destination, LocalDate date);
}
