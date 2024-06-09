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
import com.BlogAuJ.AuJ.api.dto.request.CategoryRequest;
import com.BlogAuJ.AuJ.api.dto.response.CategoryResponse;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.ICategotyService;
import com.BlogAuJ.AuJ.util.enums.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path ="/categories")
@AllArgsConstructor

public class CategoryController {

    @Autowired
    private final ICategotyService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType))
            sortType = SortType.NONE;

        return ResponseEntity.ok(this.categoryService.getAll(page - 1, size, sortType));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> get(
            @PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(this.categoryService.create(request));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryResponse> update(
            @RequestBody CategoryRequest request,
            @PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    
}
