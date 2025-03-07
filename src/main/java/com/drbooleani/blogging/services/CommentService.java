package com.drbooleani.blogging.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.drbooleani.blogging.dtos.CommentPageResponse;
import com.drbooleani.blogging.dtos.CommentResponse;
import com.drbooleani.blogging.dtos.UserCommentRequest;
import com.drbooleani.blogging.models.Comment;
import com.drbooleani.blogging.models.Post;
import com.drbooleani.blogging.models.User;
import com.drbooleani.blogging.repositories.CommentRepository;
import com.drbooleani.blogging.repositories.PostRepository;
import com.drbooleani.blogging.repositories.UserRepository;
import com.drbooleani.blogging.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	public CommentService(PostRepository postRepository, CommentRepository commentRepository,
			UserRepository userRepository) {
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
		this.userRepository = userRepository;
	}

	public Page<CommentPageResponse> getAllCommentsByPost(Integer postId, Pageable pageable) {
		this.findPostById(postId);
		Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);
		return commentsPage.map(comment -> convertToPageResponse(comment));
	}

	@Transactional
	public CommentResponse createComment(UserCommentRequest userCommentRequest) {
		var post = this.findPostById(userCommentRequest.postId());
		var user = this.findUserById(userCommentRequest.userId());
		var comment = this.commentRepository.save(makeComment(userCommentRequest, post, user));
		return convertToResponse(comment);
	}

	@Transactional
	public CommentResponse updateComment(Integer commentId, UserCommentRequest userCommentRequest) {
		Comment comment = this.findCommentById(commentId);
		comment.setId(commentId);
		comment.setContent(userCommentRequest.content());
		comment.setCreatedAt(this.findCommentById(commentId).getCreatedAt());
		comment = commentRepository.save(comment);
		return convertToResponse(comment);
	}
	
	@Transactional
    public void deleteComment(Integer commentId) {
        Comment comment = this.findCommentById(commentId);
        commentRepository.delete(comment);
    }

	private Comment findCommentById(Integer commentId) {
		return this.commentRepository.findById(commentId)
				  .orElseThrow(() -> new ResourceNotFoundException("Comment was not found!"));
	}
	
	private CommentResponse convertToResponse(Comment comment) {
		return new CommentResponse(comment.getId(), comment.getContent(), comment.getUser().getId().toString(),
				comment.getUser().getFullName(), comment.getUser().getProfileUrl(), comment.getPost().getId(),
				comment.getPost().getTitle(), comment.getCreatedAt(), comment.getUpdatedAt());
	}

	private Comment makeComment(UserCommentRequest userCommentRequest, Post post, User user) {
		var comment = new Comment();
		comment.setContent(userCommentRequest.content());
		comment.setPost(post);
		comment.setUser(user);
		return comment;
	}

	private User findUserById(Integer id) {
		return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User was not found!"));
	}

	private CommentPageResponse convertToPageResponse(Comment comment) {
		return new CommentPageResponse(comment.getId(), comment.getContent(), comment.getUser().getId(), comment.getUser().getFullName(),
				comment.getUser().getProfileUrl(), comment.getCreatedAt(), comment.getUpdatedAt());
	}

	private Post findPostById(Integer id) {
		return this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post was not found!"));
	}

}
