package com.tatkal.Repository;

import com.tatkal.model.TrainCoachDetails;
import com.tatkal.model.TrainCoachDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainCoachDetailsRepository extends JpaRepository<TrainCoachDetails, TrainCoachDetailsId> {

}
