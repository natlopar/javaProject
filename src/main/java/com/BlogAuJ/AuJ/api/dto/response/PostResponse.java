package com.BlogAuJ.AuJ.api.dto.response;


import java.time.LocalDate;
import java.util.List;

import com.BlogAuJ.AuJ.util.enums.StatusComment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private LocalDate publicationDate;
    private String title;
    private String subtitle;
    private String content;
    private String image;
    private int likes;
    private Boolean feature;
    private StatusComment status;
    private String mediaUrl;
    private String mediaFilename;
    private int views;

    private UserBasicResponse user;
    private List<CommentBasicResponse> comment;
    private FavouriteBasicResponse favourite;
    private CategoryBasicResponse category;
    
    
}
