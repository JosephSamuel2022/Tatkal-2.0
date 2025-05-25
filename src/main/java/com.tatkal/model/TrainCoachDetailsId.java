package com.tatkal.model;

import java.io.Serializable;
import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainCoachDetailsId implements Serializable {
    private String trainId;
    private Date date;  // or LocalDate, depending on your choice
}