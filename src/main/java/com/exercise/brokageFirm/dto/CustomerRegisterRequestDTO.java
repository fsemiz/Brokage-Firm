package com.exercise.brokageFirm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterRequestDTO {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 20, message = "Password must be between 3 and 20 characters")
    private String password;

    private boolean isAdmin;
    // Getter metodu eklenmeli!
    public Boolean getIsAdmin() { return isAdmin; }

    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }

}