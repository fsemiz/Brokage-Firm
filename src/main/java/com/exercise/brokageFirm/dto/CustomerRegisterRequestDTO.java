package com.exercise.brokageFirm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterRequestDTO {

    private String username;

    private String password;

    private boolean isAdmin;
}