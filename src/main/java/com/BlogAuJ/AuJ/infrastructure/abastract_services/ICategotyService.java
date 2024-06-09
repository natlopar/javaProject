package com.BlogAuJ.AuJ.infrastructure.abastract_services;

import com.BlogAuJ.AuJ.api.dto.request.CategoryRequest;
import com.BlogAuJ.AuJ.api.dto.response.CategoryResponse;

public interface ICategotyService extends CrudService<CategoryRequest, CategoryResponse,Long>{
    public final String FIELD_BY_SORT = "name";
}
    

