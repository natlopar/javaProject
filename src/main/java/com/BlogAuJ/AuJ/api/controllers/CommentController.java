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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BlogAuJ.AuJ.api.dto.request.CommentRequest;
import com.BlogAuJ.AuJ.api.dto.response.CommentResponse;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.ICommentService;
import com.BlogAuJ.AuJ.util.enums.SortType;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/comments")
@AllArgsConstructor
public class CommentController {

    @Autowired
    private final ICommentService commentService;

     @GetMapping
    public ResponseEntity<Page<CommentResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType))
            sortType = SortType.NONE;

        return ResponseEntity.ok(this.commentService.getAll(page - 1, size, sortType));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentResponse> get(
            @PathVariable Long id) {
        return ResponseEntity.ok(this.commentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> insert(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(this.commentService.create(request));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommentResponse> update(
            @RequestBody CommentRequest request,
            @PathVariable Long id) {
        return ResponseEntity.ok(this.commentService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
