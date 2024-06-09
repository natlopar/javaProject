package com.BlogAuJ.AuJ.infrastructure.abastract_services;

import java.util.List;

import com.BlogAuJ.AuJ.api.dto.request.PostRequest;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostResponse;


public interface IPostService extends CrudService<PostRequest, PostResponse, Long> {
    public final String FIELD_BY_SORT = "publicationDate";


    public List<PostBasicResponse> findAllByTitle(String title );
    
}
