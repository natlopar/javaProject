package com.BlogAuJ.AuJ.api.dto.request;

import com.BlogAuJ.AuJ.util.enums.StatusRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

   
    @Size(min=5 , max=50, message="User must be between 5 and 50 characters")
    private String username;
    @Email(message = "Is not valid email")
    private String email;
    private String avatar;
    private StatusRole role;
    
}
