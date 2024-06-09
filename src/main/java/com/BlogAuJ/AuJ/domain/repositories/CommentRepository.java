package com.BlogAuJ.AuJ.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BlogAuJ.AuJ.domain.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    
}
