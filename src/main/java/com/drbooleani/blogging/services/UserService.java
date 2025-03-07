package com.drbooleani.blogging.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.drbooleani.blogging.dtos.UserRequest;
import com.drbooleani.blogging.dtos.UserResponse;
import com.drbooleani.blogging.dtos.UserUpdatePasswordRequest;
import com.drbooleani.blogging.models.User;
import com.drbooleani.blogging.repositories.RoleRepository;
import com.drbooleani.blogging.repositories.UserRepository;
import com.drbooleani.blogging.services.exceptions.BadRequestException;
import com.drbooleani.blogging.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(
			UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder
			
	) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Page<UserResponse> getAllUsers(Pageable pageable) {
		var users = this.userRepository.findAll(pageable);
		return users.map(user -> entityToResponse(user));
	}
	
	public UserResponse getUserById(Integer id) {
		var user = findUserById(id);
		return entityToResponse(user);
	}
	
	public UserResponse createUser(UserRequest request) {
		if (this.userRepository.existsByEmail(request.email())) {
			throw new BadRequestException("This e-mail was already used!");
		}
		var user = this.userRepository.save(createMemberUser(request));
		return entityToResponse(user);
	}
	
	public UserResponse updateProfilePhoto(Integer id, String profileUrl) {
		var user = findUserById(id);
	    user.setProfileUrl(profileUrl);
	    user = this.userRepository.save(user);
	    return entityToResponse(user);
	}
	
	public UserResponse updateUserPassword(Integer id, UserUpdatePasswordRequest request) {
	    var user = findUserById(id);
	    this.checkOldAndNewPassword(request, user);
	    user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
	    user = this.userRepository.save(user);
	    return entityToResponse(user);
	}
	
	public void deleteUser(Integer id) {
		var user = findUserById(id);
		this.userRepository.delete(user);
	}

	public UserResponse makeUserAdmin(Integer id) {
		var user = findUserById(id);
		user.setRole(this.roleRepository.findByName("ROLE_ADMIN"));
		user = this.userRepository.save(user);
		return entityToResponse(user);
	}
	
	private User findUserById(Integer id) {
		return this.userRepository.findById(id)
				  .orElseThrow(() -> new ResourceNotFoundException("User was not found!"));
	}

	private User createMemberUser(UserRequest request) {
		var user = new User();
		user.setFullName(request.fullName());
		user.setEmail(request.email());
		user.setPasswordHash(passwordEncoder.encode(request.password()));
		user.setProfileUrl("user.png");
		user.setRole(this.roleRepository.findByName("ROLE_MEMBER"));
		return user;
	}
	
	private UserResponse entityToResponse(User user) {
		return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getProfileUrl(), user.getRole().getName());
	}
	
	private void checkOldAndNewPassword(UserUpdatePasswordRequest request, User user) {
		if (!passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) {
	        throw new BadRequestException("Old Password is incorrect!.");
	    }
	    if (!request.newPassword().equals(request.confirmPassword())) {
	        throw new BadRequestException("The new and confirmed password not maches!.");
	    }
	}

}
