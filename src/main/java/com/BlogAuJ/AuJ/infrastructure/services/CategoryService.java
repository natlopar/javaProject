package com.BlogAuJ.AuJ.infrastructure.services;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.BlogAuJ.AuJ.api.dto.request.CategoryRequest;

import com.BlogAuJ.AuJ.api.dto.response.CategoryResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.domain.entities.Category;
import com.BlogAuJ.AuJ.domain.repositories.CategoryRepository;
import com.BlogAuJ.AuJ.domain.repositories.PostRepository;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.ICategotyService;
import com.BlogAuJ.AuJ.util.enums.SortType;
import com.BlogAuJ.AuJ.util.exceptions.BadRequestException;
import com.BlogAuJ.AuJ.util.exceptions.message.ErrorMesaage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService implements ICategotyService {

    @Autowired
    private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponse create(CategoryRequest request) {

        Category category = this.requestToEntity(request);

        category.setPost(new ArrayList<>());

        return this.entityToResponse(this.categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getById(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public CategoryResponse update(CategoryRequest request, Long id) {
        Category category = this.find(id);


        category =this.requestToEntity(request);
        category.setPost(new ArrayList<>());
        category.setId(id);

        return this.entityToResponse(this.categoryRepository.save(category));

    }

    @Override
    public void delete(Long id) {
        this.categoryRepository.delete(this.find(id));

    }

    @Override
    public Page<CategoryResponse> getAll(int page, int size, SortType sortType) {
         if (page < 0)
            page = 0;

        PageRequest pagination = null;

        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        pagination = PageRequest.of(page, size);
        
        return this.categoryRepository.findAll(pagination)
                .map(this::entityToResponse);
    }







    private CategoryResponse entityToResponse(Category entity){

        List<PostBasicResponse> posts = entity.getPost()
                .stream()
                .map(temp -> PostBasicResponse.builder()
                        .id(temp.getId())
                        .publicationDate(temp.getPublicationDate())
                        .title(temp.getTitle())
                        .content(temp.getContent())
                        .build())
                .collect(Collectors.toList());

        return CategoryResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .posts(posts)
        .build();
    }

    private Category requestToEntity(CategoryRequest request){
        return Category.builder()
                    .name(request.getName())
                    .build();
    }


    private Category find(Long id){
        return this.categoryRepository.findById(id).orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("category")));
    }

    
}
