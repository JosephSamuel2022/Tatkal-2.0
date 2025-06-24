package com.tatkal.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainCoachDetailsId implements Serializable {
  private String trainId;
  private LocalDate journeyDate;
  private String coachType;
}
