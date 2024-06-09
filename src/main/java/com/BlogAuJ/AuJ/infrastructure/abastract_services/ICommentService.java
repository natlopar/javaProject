package com.BlogAuJ.AuJ.infrastructure.abastract_services;

import com.BlogAuJ.AuJ.api.dto.request.CommentRequest;
import com.BlogAuJ.AuJ.api.dto.response.CommentResponse;

public interface ICommentService extends CrudService<CommentRequest, CommentResponse, Long > {
    public final String FIELD_BY_SORT = "dateTime";
}
