package com.BlogAuJ.AuJ.api.dto.response;

import com.BlogAuJ.AuJ.util.enums.StatusRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicResponse {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private StatusRole role;
    
}
