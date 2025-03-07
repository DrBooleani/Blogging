package com.drbooleani.blogging.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.drbooleani.blogging.dtos.CommentPageResponse;
import com.drbooleani.blogging.dtos.CommentResponse;
import com.drbooleani.blogging.dtos.UserCommentRequest;
import com.drbooleani.blogging.services.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<Page<CommentPageResponse>> getAllComments(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
    	String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "asc".equalsIgnoreCase(sortParams[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return ResponseEntity.ok(this.commentService.getAllComments(pageable));
    }
    
    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentPageResponse>> getCommentsByPost(@PathVariable Integer postId,
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "createdAt,desc") String sort) {
        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "asc".equalsIgnoreCase(sortParams[1]) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
        return ResponseEntity.ok(this.commentService.getAllCommentsByPost(postId, pageable));
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody UserCommentRequest userCommentRequest) {
        CommentResponse createdComment = commentService.createComment(userCommentRequest);
        URI uri = URI.create("/api/v1/comments/" + createdComment.id());
        return ResponseEntity.created(uri).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Integer id,
                                                         @Valid @RequestBody UserCommentRequest userCommentRequest) {
        CommentResponse updatedComment = commentService.updateComment(id, userCommentRequest);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
