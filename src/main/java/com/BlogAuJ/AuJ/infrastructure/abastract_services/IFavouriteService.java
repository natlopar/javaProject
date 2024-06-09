package com.BlogAuJ.AuJ.infrastructure.abastract_services;

import com.BlogAuJ.AuJ.api.dto.request.FavouriteRequest;
import com.BlogAuJ.AuJ.api.dto.response.FavouriteResponse;

public interface IFavouriteService extends CrudService<FavouriteRequest, FavouriteResponse, Long> {
    public final String FIELD_BY_SORT = "title";
}
