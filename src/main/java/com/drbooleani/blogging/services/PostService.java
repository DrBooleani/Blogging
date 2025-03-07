package com.drbooleani.blogging.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.drbooleani.blogging.dtos.PostPageResponse;
import com.drbooleani.blogging.dtos.PostRequest;
import com.drbooleani.blogging.dtos.PostResponse;
import com.drbooleani.blogging.models.Post;
import com.drbooleani.blogging.repositories.PostRepository;
import com.drbooleani.blogging.services.exceptions.ResourceNotFoundException;

@Service
public class PostService {
	
	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	public Page<PostPageResponse> getAllPosts(Pageable pageable) {
		var posts = this.postRepository.findAll(pageable);
		return posts.map(p -> convertToPageResponse(p));
	}
	
	public PostResponse getPostById(Integer id) {
		var post = findPostById(id);
		return this.convertToPostResponse(post);
	}
	
	public PostResponse createPost(PostRequest request) {
		var post = this.convertToEntity(request);
		var response = this.postRepository.save(post);
		return convertToPostResponse(response);
	}
	
	public PostResponse updatePost(Integer id, PostRequest request) {
		var post = this.findPostById(id);
		post = this.convertToEntity(request);
		post.setId(id);
		post.setCreatedAt(this.findPostById(id).getCreatedAt());
		var response = this.postRepository.save(post);
		return convertToPostResponse(response);
	}
	
	public void deletePost(Integer id) {
		var post = findPostById(id);
		this.postRepository.delete(post);
	}
	
	public PostResponse updateThumbnail(Integer id, String newThumbnail) {
	    var post = this.findPostById(id);
	    post.setThumbnail(newThumbnail);
	    var updatedPost = this.postRepository.save(post);
	    return convertToPostResponse(updatedPost);
	}

	
	private Post findPostById(Integer id) {
		return this.postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post was not found!"));
	}
	
	private Post convertToEntity(PostRequest request) {
		var post = new Post();
		post.setTitle(request.title());
		post.setCategory(request.category());
		post.setThumbnail(request.thumbnail());
		post.setContent(request.content());
		post.setTags(request.tags());
		return post;
		
	}
	private PostPageResponse convertToPageResponse(Post post) {
		return new PostPageResponse(
				post.getId(), 
				post.getTitle(), 
				post.getThumbnail(), 
				post.getCategory(), 
				post.getTags(), 
				post.getCreatedAt(), 
				post.getUpdatedAt()
		);
	}
	
	private PostResponse convertToPostResponse(Post post) {
		return new PostResponse(
				post.getId(), 
				post.getTitle(), 
				post.getThumbnail(),
				post.getContent(),
				post.getCategory(), 
				post.getTags(), 
				post.getCreatedAt(), 
				post.getUpdatedAt()
		);
	}

}
