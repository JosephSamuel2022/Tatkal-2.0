package com.tatkal.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@IdClass(TrainCoachDetailsId.class)
public class TrainCoachDetails {

    @Id
    private String trainId;
    @Id
    private LocalDate date;
    private String coachType;
    private String coach;
    private int totalSeats;
    @Lob
    private List<Integer> Lower_Berth_Balance_Seat_No; // Sleeper/AC Coaches
    @Lob
    private List<Integer> Middle_Berth_Balance_Seat_No; // Sleeper/AC Coaches
    @Lob
    private List<Integer> Upper_Berth_Balance_Seat_No; // Sleeper/AC Coaches
    @Lob
    private List<Integer> Side_Lower_Berth_Balance_Seat_No; // Sleeper/AC Coaches
    @Lob
    private List<Integer> Side_Upper_Berth_Balance_Seat_No; // Sleeper/AC Coaches
    @Lob
    private List<Integer> Window_Balance_Seat_No; // Second Sitting/Chair Car
    @Lob
    private List<Integer> Middle_Balance_Seat_No; // Second Sitting/Chair Car
    @Lob
    private List<Integer> Asile_Balance_Seat_No; // Second Sitting/Chair Car

}
