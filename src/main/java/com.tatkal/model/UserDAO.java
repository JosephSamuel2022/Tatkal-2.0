package com.tatkal.model;


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
public class UserDAO {
    @Id
    private String userId;
    private String userName;
    private String password;
}