package com.tatkal.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDAO {
    private String name;
    private String userId;
    private String paymentId;
    private Long quantity;
    private Long amount;
}
