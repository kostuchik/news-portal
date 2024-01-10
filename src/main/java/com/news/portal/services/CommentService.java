package com.news.portal.services;

import com.news.portal.dto.CommentDTO;
import com.news.portal.exception.custom_exception.CommentException;
import com.news.portal.models.Comment;
import com.news.portal.models.User;
import com.news.portal.repositories.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;
    public Comment findCommentById(Long id) {
        return commentRepository.findCommentById(id)
                .orElseThrow(() -> new EntityNotFoundException("The comment with id = " + id + " was not found!"));
    }
    public Comment createComment(String authorName, Long newsId, CommentDTO commentDTO) {
        User author = (User) userService.loadUserByUsername(authorName);
        Comment newComment = new Comment(
                new Date(),
                commentDTO.getText(),
                commentDTO.getParentId(),
                author,
                newsService.findNewsById(newsId));
        return save(newComment);
    }
    public Comment save(Comment comment) {
        try {
            return commentRepository.save(comment);

        } catch (RuntimeException e) {
            log.error("Сomment {} has not been saved. Error: [{}]", comment.getAuthor(), e);
            throw new PersistenceException(String.format("Comment %s has not been saved. Error: [%s].", comment.getId(), e));
        }
    }
    public Comment updateComment(String username, Long commentId, CommentDTO commentDTO) {
        if (matchersUser(username, commentId))
            throw new CommentException(String.format("User %s cannot edit a comment with id = %d!",
                    username, commentId));

        Comment comment = findCommentById(commentId);
        comment.setText(commentDTO.getText());
        return save(comment);
    }
    /**
     * Checking that the comment corresponds to the user with the specified username and that the comment exists.
     *
     * @param username  - the username of the comment creator
     * @param commentId - comment id
     */
    public boolean matchersUser(String username, Long commentId) {
        return username.equals(findCommentById(commentId).getAuthor().getUsername()) && commentRepository.existsById(commentId);
    }
    public void deleteById(String username, Long commentId) {
        if (!matchersUser(username, commentId))
            throw new CommentException(String.format("User %s cannot delete a comment with id = %d!",
                    username, commentId));

        commentRepository.deleteById(commentId);
    }
}
