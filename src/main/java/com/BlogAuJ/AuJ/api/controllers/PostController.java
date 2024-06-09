package com.BlogAuJ.AuJ.api.controllers;

import java.util.List;
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

import com.BlogAuJ.AuJ.api.dto.request.CommentRequest;
import com.BlogAuJ.AuJ.api.dto.request.PostRequest;
import com.BlogAuJ.AuJ.api.dto.response.CommentResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostResponse;
import com.BlogAuJ.AuJ.api.dto.response.UserBasicResponse;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.IPostService;
import com.BlogAuJ.AuJ.util.enums.SortType;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path="/posts")
@AllArgsConstructor
public class PostController {

    @Autowired
    private final IPostService postService;

     @GetMapping
    public ResponseEntity<Page<PostResponse>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType))
            sortType = SortType.NONE;

        return ResponseEntity.ok(this.postService.getAll(page-1, size, sortType));
    }

    @GetMapping(path = "/search/{title}")
    public ResponseEntity<List<PostBasicResponse>> findAllTitle(
        @PathVariable String title) {
            return ResponseEntity.ok(this.postService.findAllByTitle(title));
        
    }


    @PostMapping
    public ResponseEntity<PostResponse> insert(
        @RequestBody PostRequest request) {
        return ResponseEntity.ok(this.postService.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PostResponse> get(
            @PathVariable Long id) {
        return ResponseEntity.ok(this.postService.getById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<PostResponse> update(
            @RequestBody PostRequest request,
            @PathVariable Long id) {
        return ResponseEntity.ok(this.postService.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.postService.delete(id);
        return ResponseEntity.noContent().build();
    }





    
}
