package com.BlogAuJ.AuJ.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BlogAuJ.AuJ.api.dto.request.FavouriteRequest;
import com.BlogAuJ.AuJ.api.dto.response.FavouriteResponse;
import com.BlogAuJ.AuJ.infrastructure.services.FavouriteService;
import com.BlogAuJ.AuJ.util.enums.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/favourites")
@AllArgsConstructor
public class FavouriteController {

    @Autowired
    private final FavouriteService favouriteService;


    @GetMapping
    public ResponseEntity<Page<FavouriteResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType))
            sortType = SortType.NONE;

        return ResponseEntity.ok(this.favouriteService.getAll(page-1, size, sortType));
    }


    @PostMapping
    public ResponseEntity<FavouriteResponse> insert(
        @RequestBody FavouriteRequest request) {
        return ResponseEntity.ok(this.favouriteService.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FavouriteResponse> get(
            @PathVariable Long id) {
        return ResponseEntity.ok(this.favouriteService.getById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<FavouriteResponse> update(
            @RequestBody FavouriteRequest request,
            @PathVariable Long id) {
        return ResponseEntity.ok(this.favouriteService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.favouriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
