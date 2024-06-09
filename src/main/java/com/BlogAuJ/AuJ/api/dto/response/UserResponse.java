package com.BlogAuJ.AuJ.api.dto.response;

import java.util.List;

import com.BlogAuJ.AuJ.domain.entities.Favourite;
import com.BlogAuJ.AuJ.domain.entities.Post;
import com.BlogAuJ.AuJ.util.enums.StatusRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String avatar;
    private StatusRole role;
    private List<CommentBasicResponse> comment;
    private List<PostBasicResponse> posts;
    private List<FavouriteBasicResponse> favourites;
    
    
}
