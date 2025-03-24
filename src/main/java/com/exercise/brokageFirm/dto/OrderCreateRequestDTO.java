package com.exercise.brokageFirm.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDTO {

    private Long customerId;

    private String assetName;

    private String side; // BUY / SELL

    private int size;

    private int price;
}