package com.BlogAuJ.AuJ.infrastructure.abastract_services;

import com.BlogAuJ.AuJ.api.dto.request.UserRequest;
import com.BlogAuJ.AuJ.api.dto.response.UserResponse;

public interface IUserService extends CrudService<UserRequest, UserResponse, Long> {
    
}
