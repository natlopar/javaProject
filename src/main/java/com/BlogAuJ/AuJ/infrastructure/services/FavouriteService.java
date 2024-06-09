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

import com.BlogAuJ.AuJ.api.dto.request.FavouriteRequest;
import com.BlogAuJ.AuJ.api.dto.response.FavouriteResponse;
import com.BlogAuJ.AuJ.api.dto.response.PostBasicResponse;
import com.BlogAuJ.AuJ.api.dto.response.UserBasicResponse;
import com.BlogAuJ.AuJ.domain.entities.Favourite;
import com.BlogAuJ.AuJ.domain.entities.Post;
import com.BlogAuJ.AuJ.domain.entities.User;
import com.BlogAuJ.AuJ.domain.repositories.FavouriteRepository;
import com.BlogAuJ.AuJ.domain.repositories.PostRepository;
import com.BlogAuJ.AuJ.domain.repositories.UserRepository;
import com.BlogAuJ.AuJ.infrastructure.abastract_services.IFavouriteService;
import com.BlogAuJ.AuJ.util.enums.SortType;
import com.BlogAuJ.AuJ.util.exceptions.BadRequestException;
import com.BlogAuJ.AuJ.util.exceptions.message.ErrorMesaage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FavouriteService implements IFavouriteService {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final PostService postService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final FavouriteRepository favouriteRepository;

    @Autowired
    private final UserService userService;

    @Override
    public FavouriteResponse create(FavouriteRequest request) {
        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));
        Post post = this.postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("post")));

        Favourite favouriteToSave = this.requestToEntity(request);
        favouriteToSave.setUser(user);
        favouriteToSave.setPost(post);
        Favourite favourite = favouriteRepository.save(favouriteToSave);

        return this.entityToResponse(favourite);
    }

    @Override
    public FavouriteResponse getById(Long id) {
        return this.entityToResponse(this.find(id));
    }

    @Override
    public FavouriteResponse update(FavouriteRequest request, Long id) {
        Favourite favourite = this.find(id);

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("user")));

        favourite = this.requestToEntity(request);

        favourite.setUser(user);
        favourite.setId(id);

        return entityToResponse(this.favouriteRepository.save(favourite));

    }

    @Override
    public void delete(Long id) {
        this.favouriteRepository.delete(this.find(id));
    }

    @Override
    public Page<FavouriteResponse> getAll(int page, int size, SortType sortType) {
        if (page < 0)
            page = 0;

        PageRequest pagination = null;

        switch (sortType) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }

        pagination = PageRequest.of(page, size);

        return this.favouriteRepository.findAll(pagination)
                .map(this::entityToResponse);
    }

    private FavouriteResponse entityToResponse(Favourite entity) {

        UserBasicResponse user = new UserBasicResponse();
        BeanUtils.copyProperties(entity.getUser(), user);

        return FavouriteResponse.builder()
                .id(entity.getId())
                
                .user(userService.entityToBasicResponse(entity.getUser()))
                .post(postService.entityToBasicResponse(entity.getPost()))
                .build();

    }

    private Favourite requestToEntity(FavouriteRequest request) {

        return Favourite.builder()
                
                .build();
    }

    private Favourite find(Long id) {
        return this.favouriteRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorMesaage.idNotFound("favourite")));
    }

}
