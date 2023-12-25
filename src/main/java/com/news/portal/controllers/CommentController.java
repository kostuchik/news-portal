package com.news.portal.controllers;

import com.news.portal.dto.CommentDTO;
import com.news.portal.models.Comment;
import com.news.portal.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.findCommentById(commentId));
    }

    @PostMapping("/{newsId}")
    public ResponseEntity<?> createComment(@PathVariable Long newsId,
                                           @RequestBody CommentDTO commentDTO,
                                           Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok().body(commentService.createComment(username, newsId, commentDTO));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable Long commentId,
                                         @RequestBody Comment comment) {
        return ResponseEntity.ok().body(commentService.updateComment( commentId, comment));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.ok().body("User's has been delete.");
    }
}
