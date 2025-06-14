package com.tatkal.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "train_coach_details")
@IdClass(TrainCoachDetailsId.class)
public class TrainCoachDetails {

  @Id
  @Column(name = "train_id", nullable = false)
  private String trainId;

  @Id
  @Column(name = "journey_date", nullable = false)
  private LocalDate date;

  @Id
  @Column(name = "coach_type", nullable = false)
  private String coachType;

  @Column(name = "coach", nullable = false)
  private String coach;

  @Column(name = "total_seat", nullable = false)
  private Integer totalSeat;

  @ElementCollection
  @CollectionTable(
    name = "upper_balance_seats",
    joinColumns = {
      @JoinColumn(name = "train_id", referencedColumnName = "train_id"),
      @JoinColumn(name = "journey_date", referencedColumnName = "journey_date"),
      @JoinColumn(name = "coach_type", referencedColumnName = "coach_type")
    }
  )
  @Column(name = "seat_number")
  private List<Integer> upperBalanceSeatNo;

  @ElementCollection
  @CollectionTable(
    name = "lower_balance_seats",
    joinColumns = {
      @JoinColumn(name = "train_id", referencedColumnName = "train_id"),
      @JoinColumn(name = "journey_date", referencedColumnName = "journey_date"),
      @JoinColumn(name = "coach_type", referencedColumnName = "coach_type")
    }
  )
  @Column(name = "seat_number")
  private List<Integer> lowerBalanceSeatNo;

  @ElementCollection
  @CollectionTable(
    name = "middle_balance_seats",
    joinColumns = {
      @JoinColumn(name = "train_id", referencedColumnName = "train_id"),
      @JoinColumn(name = "journey_date", referencedColumnName = "journey_date"),
      @JoinColumn(name = "coach_type", referencedColumnName = "coach_type")
    }
  )
  @Column(name = "seat_number")
  private List<Integer> middleBalanceSeatNo;


}
