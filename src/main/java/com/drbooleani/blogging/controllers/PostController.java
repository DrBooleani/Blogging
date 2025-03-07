package com.drbooleani.blogging.controllers;

import java.io.IOException;
import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.drbooleani.blogging.dtos.PostPageResponse;
import com.drbooleani.blogging.dtos.PostRequest;
import com.drbooleani.blogging.dtos.PostResponse;
import com.drbooleani.blogging.services.FileStorageService;
import com.drbooleani.blogging.services.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/post")
public class PostController {

	private final FileStorageService fileStorageService;
	private final PostService postService;

	public PostController(FileStorageService fileStorageService, PostService postService) {
		this.fileStorageService = fileStorageService;
		this.postService = postService;
	}

	@GetMapping
	public ResponseEntity<Page<PostPageResponse>> getAllPosts(@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "createdAt,desc") String sort) {
		String[] sortParams = sort.split(",");
		String sortField = sortParams[0];
		Sort.Direction direction = sortParams.length > 1 && "asc".equalsIgnoreCase(sortParams[1]) ? Sort.Direction.ASC
				: Sort.Direction.DESC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
		return ResponseEntity.ok(this.postService.getAllPosts(pageable));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<PostResponse> getPostById(@PathVariable Integer id) {
		return ResponseEntity.ok(this.postService.getPostById(id));
	}

	@PostMapping
	public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request,
			@RequestParam(required = false) MultipartFile thumbnailFile) throws IOException {
		String thumbnail = (thumbnailFile != null && !thumbnailFile.isEmpty())
				? fileStorageService.storePostThumbnail(thumbnailFile, "post-" + System.currentTimeMillis())
				: "no-thumbnail.png";

		PostRequest postRequest = new PostRequest(request.title(), thumbnail, request.content(), request.category(),
				request.tags());

		PostResponse createdPost = this.postService.createPost(postRequest);
		
		URI uri = ServletUriComponentsBuilder
				  .fromCurrentContextPath()
				  .path("/{id}")
				  .buildAndExpand(createdPost.id())
				  .toUri();
		return ResponseEntity.created(uri).body(createdPost);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponse> updatePost(@PathVariable Integer id, @Valid @RequestBody PostRequest request,
			@RequestParam(required = false) MultipartFile thumbnailFile) throws IOException {
		String thumbnail = (thumbnailFile != null && !thumbnailFile.isEmpty())
				? fileStorageService.storePostThumbnail(thumbnailFile, "post-" + System.currentTimeMillis())
				: request.thumbnail();

		PostRequest postRequest = new PostRequest(request.title(), thumbnail, request.content(), request.category(),
				request.tags());

		PostResponse updatedPost = this.postService.updatePost(id, postRequest);
		return ResponseEntity.ok(updatedPost);
	}

	@PatchMapping("/{id}/thumbnail")
	public ResponseEntity<PostResponse> updateThumbnail(
	        @PathVariable Integer id, 
	        @RequestParam("thumbnailFile") MultipartFile thumbnailFile) throws IOException {
		
	    String newThumbnail = fileStorageService.storePostThumbnail(thumbnailFile, "post-thumbnail-" + id);

	    PostResponse updatedPost = postService.updateThumbnail(id, newThumbnail);

	    return ResponseEntity.ok(updatedPost);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
		this.postService.deletePost(id);
		return ResponseEntity.noContent().build();
	}

}
