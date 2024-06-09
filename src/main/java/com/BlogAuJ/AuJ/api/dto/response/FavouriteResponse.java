package com.BlogAuJ.AuJ.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponse {

    private Long id;

    private PostBasicResponse post;
    private UserBasicResponse user;
    
}
