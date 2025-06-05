package com.tatkal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@IdClass(TrainCoachDetailsId.class)
public class TrainAvailabilityDetails {

    @Id
    private String trainId;
    @Id
    private LocalDate date;
    private LocalTime time;
    private String trainName;
    private String source;
    private String destination;

    private String [] stops;

    // Regular Quota Seat Totals
    private int totalSeatsSL;    // Sleeper
    private int totalSeats2S;    // Second Sitting
    private int totalSeatsCC;    // Chair Car
    private int totalSeats3A;    // AC 3 Tier
    private int totalSeats2A;    // AC 2 Tier
    private int totalSeats1A;    // AC 1 Tier

    // Tatkal Quota Seat Totals
    private int totalTatkalSeatsSL;
    private int totalTatkalSeats2S;
    private int totalTatkalSeatsCC;
    private int totalTatkalSeats3A;
    private int totalTatkalSeats2A;
    private int totalTatkalSeats1A;
}
