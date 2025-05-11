package com.tatkal.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User{
    private String userId;
    private String userName;
    private String password;
}