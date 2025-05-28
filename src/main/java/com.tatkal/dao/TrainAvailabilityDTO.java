package com.tatkal.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainAvailabilityDTO {
    private String trainId;
    private String trainName;
    private String source;
    private String destination;
    private LocalDate travelDate;

    private int SL;
    private int S2;
    private int CC;
    private int A3;
    private int A2;
    private int A1;
}
