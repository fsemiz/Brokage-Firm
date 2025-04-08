package com.exercise.brokageFirm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequestDTO {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Asset name cannot be blank")
    private String assetName;

    @NotBlank(message = "Side (BUY/SELL) is required")
    private String side; // BUY / SELL

    @NotNull(message = "Size is required")
    @Positive(message = "Size must be positive")
    private int size;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private int price;
}