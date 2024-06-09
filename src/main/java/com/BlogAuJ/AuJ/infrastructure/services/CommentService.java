package com.BlogAuJ.AuJ.infrastructure.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.BlogAuJ.AuJ.api.dto.request.CommentRequest;
import com.BlogAuJ.AuJ.api.dto.response.CommentResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.UserResponse;
import com.BlogAuJ.AuJ.domain.entities.Comment;
import com.BlogAuJ.AuJ.domain.entities.Post;
import com.BlogAuJ.AuJ.domain.entities.User;
import com.BlogAuJ.AuJ.domain.repositories.CommentRepository;
import com.BlogAuJ.AuJ.domain.repositories.PostRepository;
import com.BlogAuJ.AuJ.domain.repositories.UserRepository;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.ICommentService;
import com.BlogAuJ.AuJ.util.enums.SortType;
import com.BlogAuJ.AuJ.util.exceptions.BadRequestException;
import com.BlogAuJ.AuJ.util.exceptions.message.ErrorMesaage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService implements ICommentService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Override
    public CommentResponse create(CommentRequest request) {

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));

        Post post = this.postRepository.findById(request.getPostId())
                .orElseThrow(()-> new BadRequestException(ErrorMesaage.idNotFound("post")));

        Comment comment = this.requestToEntity(request);

        comment.setUser(user);
        comment.setPost(post);

        return this.entityToResponse(this.commentRepository.save(comment));
    }

    @Override
    public CommentResponse getById(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public CommentResponse update(CommentRequest request, Long id) {
        
        Comment comment = this.find(id);

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));

        Post post = this.postRepository.findById(request.getPostId())
                .orElseThrow(()-> new BadRequestException(ErrorMesaage.idNotFound("post")));

        comment = this.requestToEntity(request);

        comment.setUser(user);
        comment.setPost(post);
        comment.setId(id);

        return this.entityToResponse(this.commentRepository.save(comment));

    }

    @Override
    public void delete(Long id) {
        this.commentRepository.delete(this.find(id));
    }

    @Override
    public Page<CommentResponse> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0;

        PageRequest pagination = null;

        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        return this.commentRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    private CommentResponse entityToResponse(Comment entity) {
        
        UserResponse user = new UserResponse();
        BeanUtils.copyProperties(entity.getUser(), user);
        
        PostBasicResponse post = new PostBasicResponse();
        BeanUtils.copyProperties(entity.getPost(), post);


        return CommentResponse.builder()
                .id(entity.getId())
                .contect(entity.getContent())
                .dataTime(entity.getDateTime())
                .status(entity.getStatus())
                .likes(entity.getLikes())
                .user(user)
                .postResponse(post)
                .build();
    }

    private Comment requestToEntity(CommentRequest request) {

      
        return Comment.builder()
                .content(request.getContect())
                .dateTime(request.getDataTime())
                .status(request.getStatus())
                .likes(request.getLikes())
                .build();
    }

    private Comment find(Long id) {
        return this.commentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("comment")));
    }

}
