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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.drbooleani.blogging.dtos.UserRequest;
import com.drbooleani.blogging.dtos.UserResponse;
import com.drbooleani.blogging.dtos.UserUpdatePasswordRequest;
import com.drbooleani.blogging.services.FileStorageService;
import com.drbooleani.blogging.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
	
	private final UserService userService;
	private final FileStorageService fileStorageService;
	
	public UserController(UserService userService, FileStorageService fileStorageService) {
		this.userService = userService;
		this.fileStorageService = fileStorageService;
	}
	
	@GetMapping
	public ResponseEntity<Page<UserResponse>> getAllUsers(
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "fullName,asc") String sort
	) {
	    String[] sortParams = sort.split(",");
	    String sortField = sortParams[0];
	    Sort.Direction direction = sortParams.length > 1 && "asc".equalsIgnoreCase(sortParams[1])
	            ? Sort.Direction.ASC : Sort.Direction.DESC;

	    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));
	    return ResponseEntity.ok(userService.getAllUsers(pageable));
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<UserResponse> getUserById(
			@PathVariable Integer id
	) {
		return ResponseEntity.ok(this.userService.getUserById(id));
	}
	
	@PostMapping
	public ResponseEntity<UserResponse> createMemberUser(
			@Valid @RequestBody UserRequest request
	) {
		var user = this.userService.createUser(request);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/{id}")
				.buildAndExpand(user.id())
				.toUri();
		return ResponseEntity.created(uri).body(user);
	}
	
	@PatchMapping(path = "/{id}/profile-photo")
	public ResponseEntity<UserResponse> updateProfilePhoto(
	        @PathVariable Integer id, 
	        @RequestParam MultipartFile file) throws IOException {
	    String profileUrl = fileStorageService.storeFile(file, id);
	    UserResponse updatedUser = userService.updateProfilePhoto(id, profileUrl);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@PatchMapping(path = "/{id}/update-password")
	public ResponseEntity<UserResponse> updateUserPassword(
	        @PathVariable Integer id,
	        @RequestBody UserUpdatePasswordRequest request) {
	    UserResponse updatedUser = this.userService.updateUserPassword(id, request);
	    return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
	    this.userService.deleteUser(id);
	    return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(path = "/{id}/make-admin")
	public ResponseEntity<UserResponse> makeUserAdmin(@PathVariable Integer id) {
	    UserResponse updatedUser = this.userService.makeUserAdmin(id);
	    return ResponseEntity.ok(updatedUser);
	}


}
