package com.tatkal.Repository;

import com.tatkal.dao.TrainCoachDetails;
import com.tatkal.dao.TrainCoachDetailsId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TrainCoachDetailsRepository extends JpaRepository<TrainCoachDetails, TrainCoachDetailsId> {

  @EntityGraph(attributePaths = {"upperBalanceSeatNo", "lowerBalanceSeatNo", "middleBalanceSeatNo"})
  Optional<TrainCoachDetails> findByTrainIdAndJourneyDateAndCoachType(String trainId, LocalDate journeyDate, String coachType);

}
