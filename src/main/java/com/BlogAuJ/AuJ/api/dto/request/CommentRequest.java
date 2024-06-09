package com.BlogAuJ.AuJ.api.dto.request;


import java.time.LocalDate;


import com.BlogAuJ.AuJ.util.enums.StatusComment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long id;
    private String contect;
    private LocalDate dataTime;
    private StatusComment status;
    private Integer likes;
    private Long userId;
    private Long postId;
    
}
