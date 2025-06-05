package com.tatkal.Repository;

import com.tatkal.model.TrainAvailabilityDetails;
import com.tatkal.model.TrainCoachDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainAvailabilityDetailsRepository extends JpaRepository<TrainAvailabilityDetails, TrainCoachDetailsId> {
    List<TrainAvailabilityDetails> findBySourceAndDestinationAndDate(String source, String destination, LocalDate date);
    TrainAvailabilityDetails findByTrainIdAndDate(String trainId, LocalDate date);


    /**
     * Finds train availability details where:
     * - both source and destination exist in the stops array,
     * - and the source appears before the destination in the stops sequence.
     * This ensures that the route is valid from source to destination following the stops order.
     */
    @Query(
            value = """
        SELECT * 
        FROM train_availability_details 
        WHERE 
            :source = ANY(stops) 
            AND :destination = ANY(stops) 
            AND array_position(stops, :source) < array_position(stops, :destination)
            AND date = :date
        """,
            nativeQuery = true
    )
    List<TrainAvailabilityDetails> findMatchingRoutes(
            @Param("source") String source,
            @Param("destination") String destination,
            @Param("date") LocalDate date
    );
}