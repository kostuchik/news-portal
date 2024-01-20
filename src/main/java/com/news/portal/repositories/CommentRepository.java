package com.news.portal.repositories;

import com.news.portal.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    Optional<Comment> findCommentById(long id);

    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findAllByNews_Id(long newsId, Pageable pageable);
}
