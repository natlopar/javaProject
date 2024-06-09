package com.BlogAuJ.AuJ.infrastructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.BlogAuJ.AuJ.api.dto.request.FavouriteRequest;
import com.BlogAuJ.AuJ.api.dto.request.UserRequest;
import com.BlogAuJ.AuJ.api.dto.response.CommentBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.FavouriteBasicResponse;

import com.BlogAuJ.AuJ.api.dto.response.FavouriteResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.UserBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.UserResponse;
import com.BlogAuJ.AuJ.domain.entities.Favourite;
import com.BlogAuJ.AuJ.domain.entities.User;
import com.BlogAuJ.AuJ.domain.repositories.UserRepository;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.IUserService;
import com.BlogAuJ.AuJ.util.enums.SortType;
import com.BlogAuJ.AuJ.util.exceptions.BadRequestException;
import com.BlogAuJ.AuJ.util.exceptions.message.ErrorMesaage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserResponse create(UserRequest request) {

        User user = this.requestToEntity(request, new User());
        user.setComment(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        user.setFavourites(new ArrayList<>());

        return this.entityToResponse(this.userRepository.save(user));

    }

    @Override
    public UserResponse getById(Long id) {
        User user = this.find(id);
        return this.entityToResponse(user);
    }

    @Override
    public UserResponse update(UserRequest request, Long id) {
        User userToUpdate = this.find(id);

        User user = this.requestToEntity(request, userToUpdate);
        user.setComment(new ArrayList<>());
        user.setPosts(new ArrayList<>());
        user.setFavourites(new ArrayList<>());
        return this.entityToResponse(this.userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = this.find(id);
        this.userRepository.delete(user);
    }

    @Override
    public Page<UserResponse> getAll(int page, int size, SortType sortType) {

        if (page < 0)
            page = 0;
        PageRequest pagination = PageRequest.of(page, size);

        return this.userRepository.findAll(pagination).map(this::entityToResponse);
    }

    public UserBasicResponse entityToBasicResponse(User entity) {
        return UserBasicResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .avatar(entity.getAvatar())
                .role(entity.getRole())
                .build();
    }

    public UserResponse entityToResponse(User entity) {

        List<CommentBasicResponse> comment = entity.getComment()
                .stream()
                .map(temp -> CommentBasicResponse.builder()
                        .id(temp.getId())
                        .contect(temp.getContent())
                        .dataTime(temp.getDateTime())
                        .status(temp.getStatus())
                        .likes(temp.getLikes())
                        .build())
                .collect(Collectors.toList());

        List<PostBasicResponse> posts = entity.getPosts()
                .stream()
                .map(temp -> PostBasicResponse.builder()
                        .id(temp.getId())
                        .publicationDate(temp.getPublicationDate())
                        .title(temp.getTitle())
                        .content(temp.getContent())
                        .build())
                .collect(Collectors.toList());

        List<FavouriteBasicResponse> favourites = entity.getFavourites()
                .stream()
                .map(temp -> FavouriteBasicResponse.builder()
                        .id(temp.getId())
                        .build())
                .collect(Collectors.toList());

        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .avatar(entity.getAvatar())
                .role(entity.getRole())
                .comment(comment)
                .posts(posts)
                .favourites(favourites)
                .build();

    }

    private User requestToEntity(UserRequest entity, User user) {
        user.setUsername(entity.getUsername());
        user.setEmail(entity.getEmail());
        user.setAvatar(entity.getAvatar());
        user.setRole(entity.getRole());
        user.setComment(new ArrayList<>());
        return user;

    }

    private User find(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));
    }

}
