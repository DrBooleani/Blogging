package com.drbooleani.blogging.models;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user")
public class User implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "full_name", length = 100, nullable = false)
	private String fullName;
	
	@Column(name = "email", length = 200, nullable = false, unique = true)
	private String email;
	
	@Column(name = "password_hash", length = 64, nullable = false)
	private String passwordHash;
	
	@Column(name = "profile_url")
	private String profile_url;
	
	@ManyToOne
	@JoinColumn(name = "role_id",referencedColumnName = "id")
	private Role role;

	public User() {
		
	}
	
	public User(Integer id, String fullName, String email, String passwordHash, String profile_url, Role role) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.passwordHash = passwordHash;
		this.profile_url = profile_url;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getProfile_url() {
		return profile_url;
	}

	public void setProfile_url(String profile_url) {
		this.profile_url = profile_url;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", profile_url=" + profile_url
				+ ", role=" + role + "]";
	}
	
}
