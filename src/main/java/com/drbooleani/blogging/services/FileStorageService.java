package com.drbooleani.blogging.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	
	// Método para armazenar a foto de perfil de usuário
	public String storeProfilePhoto(MultipartFile file, Integer userId) throws IOException {
		Path path = Paths.get(uploadDir, "profile-photos");
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		String fileName = userId + "_" + file.getOriginalFilename();
		Path targetLocation = path.resolve(fileName);
		
		Files.copy(file.getInputStream(), targetLocation);
		return fileName;
	}
	
	public String storePostThumbnail(MultipartFile file, String post) throws IOException {
		Path path = Paths.get(uploadDir, "post-thumbnails");
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		
		String fileName = post + "_" + file.getOriginalFilename();
		Path targetLocation = path.resolve(fileName);
		
		Files.copy(file.getInputStream(), targetLocation);
		return fileName;
	}
}
