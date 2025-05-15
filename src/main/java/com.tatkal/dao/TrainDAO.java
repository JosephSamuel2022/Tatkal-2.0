package com.tatkal.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class TrainDAO {
    @Id
    private String trainName;
    private String trainId;
}
