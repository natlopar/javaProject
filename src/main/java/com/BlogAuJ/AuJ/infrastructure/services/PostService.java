package com.BlogAuJ.AuJ.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.BlogAuJ.AuJ.api.dto.request.PostRequest;
import com.BlogAuJ.AuJ.api.dto.response.CategoryBasicResponse;

import com.BlogAuJ.AuJ.api.dto.response.CommentBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostResponse;
import com.BlogAuJ.AuJ.api.dto.response.UserBasicResponse;
import com.BlogAuJ.AuJ.domain.entities.Category;
import com.BlogAuJ.AuJ.domain.entities.Post;
import com.BlogAuJ.AuJ.domain.entities.User;
import com.BlogAuJ.AuJ.domain.repositories.CategoryRepository;
import com.BlogAuJ.AuJ.domain.repositories.PostRepository;
import com.BlogAuJ.AuJ.domain.repositories.UserRepository;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.IPostService;
import com.BlogAuJ.AuJ.util.enums.SortType;
import com.BlogAuJ.AuJ.util.exceptions.BadRequestException;
import com.BlogAuJ.AuJ.util.exceptions.message.ErrorMesaage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService implements IPostService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    @Override
    public PostResponse create(PostRequest request) {

        User user = this.userRepository.findById(request.getUserId())
        .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));

        Category category = this.categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("category")));
        Post post = this.requestToEntity(request);

        post.setComment(new ArrayList<>());
        
        post.setUser(user);
        post.setCategory(category);

        return this.entityToResponse(this.postRepository.save(post));
    }

    @Override
    public PostResponse getById(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public PostResponse update(PostRequest request, Long id) {

        Post post = this.find(id);

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));

        Category category = this.categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("category")));
                
        post = this.requestToEntity(request);
        
        post = this.requestToEntity(request);
        post.setComment(new ArrayList<>());
        

        post.setUser(user);
        post.setCategory(category);

        post.setId(id);

        return this.entityToResponse(this.postRepository.save(post));
    }

    @Override
    public void delete(Long id) {
        this.postRepository.delete(this.find(id));
    }

    @Override
    public Page<PostResponse> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0;

        PageRequest pagination = null;

        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        pagination = PageRequest.of(page, size);

        return this.postRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    public PostBasicResponse entityToBasicResponse(Post entity) {
        return PostBasicResponse.builder()
                .id(entity.getId())
                .publicationDate(entity.getPublicationDate())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .content(entity.getContent())
                .image(entity.getImage())
                .likes(entity.getLikes())
                .feature(entity.getFeature())
                .status(entity.getStatus())
                .mediaUrl(entity.getMediaUrl())
                .mediaFilename(entity.getMediaFilename())
                .views(entity.getViews())
                .build();
    }

    private PostResponse entityToResponse(Post entity) {

        UserBasicResponse user = new UserBasicResponse();
        BeanUtils.copyProperties(entity.getUser(), user);

        CategoryBasicResponse category = new CategoryBasicResponse();
        BeanUtils.copyProperties(entity.getCategory(), category);

        List<CommentBasicResponse> comments = entity.getComment().stream()
                .map(comment -> {
                    return CommentBasicResponse.builder()
                            .id(comment.getId())
                            .contect(comment.getContent())
                            .dataTime(comment.getDateTime())
                            .status(comment.getStatus())
                            .likes(comment.getLikes())
                            .build();
                }).collect(Collectors.toList());

        

        return PostResponse.builder()
                .id(entity.getId())
                .publicationDate(entity.getPublicationDate())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle())
                .content(entity.getContent())
                .image(entity.getImage())
                .likes(entity.getLikes())
                .feature(entity.getFeature())
                .status(entity.getStatus())
                 .mediaUrl(entity.getMediaUrl())
                .mediaFilename(entity.getMediaFilename())
                .views(entity.getViews())
                .user(user)
                .comment(comments)
                .category(category)
                .build();

    }

    private Post requestToEntity(PostRequest request) {

        return Post.builder()

                .publicationDate(request.getPublicationDate())
                .title(request.getTitle())
                .subtitle(request.getSubtitle())
                .content(request.getContent())
                .image(request.getImage())
                .likes(request.getLikes())
                .feature(request.getFeature())
                .status(request.getStatus())
                 .mediaUrl(request.getMediaUrl())
                .mediaFilename(request.getMediaFilename())
                .views(request.getViews())
                .build();
    }

    private Post find(Long id) {
        return this.postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("post")));
    }


    private PostBasicResponse entityPostBasic(Post entity){

        return PostBasicResponse.builder()
        .id(entity.getId())
        .publicationDate(entity.getPublicationDate())
        .title(entity.getTitle())
        .subtitle(entity.getSubtitle())
        .content(entity.getContent())
        .image(entity.getImage())
        .likes(entity.getLikes())
        .feature(entity.getFeature())
        .status(entity.getStatus())
         .mediaUrl(entity.getMediaUrl())
        .mediaFilename(entity.getMediaFilename())
        .views(entity.getViews())
        .build();
    }



    //Consultas personalizadas con JPA

    @Override
    public List<PostBasicResponse> findAllByTitle(String title) {
        return this.postRepository.findByTitle(title)
             .stream()
             .map(this::entityPostBasic)
            .collect(Collectors.toList());
    }

}
