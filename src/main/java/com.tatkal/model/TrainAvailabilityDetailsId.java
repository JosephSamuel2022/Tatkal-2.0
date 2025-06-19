package com.tatkal.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainAvailabilityDetailsId implements Serializable {
    private String trainId;
    private LocalDate date;  // or LocalDate, depending on your choice
}