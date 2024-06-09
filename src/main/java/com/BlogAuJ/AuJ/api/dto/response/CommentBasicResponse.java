package com.BlogAuJ.AuJ.api.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.BlogAuJ.AuJ.util.enums.StatusComment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CommentBasicResponse {
    private Long id;
    private String contect;
    private LocalDate dataTime;
    private StatusComment status;
    private Integer likes;
    
}
