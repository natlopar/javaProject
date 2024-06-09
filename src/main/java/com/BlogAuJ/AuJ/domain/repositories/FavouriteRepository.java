package com.BlogAuJ.AuJ.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogAuJ.AuJ.domain.entities.Favourite;
import com.BlogAuJ.AuJ.domain.entities.User;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    // get favorites by username
    List<Favourite> findByUser(User user);
}
