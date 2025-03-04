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
	
	public String storeFile(MultipartFile file, Integer userId) throws IOException {
		Path path = Paths.get(uploadDir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		String fileName = userId + "_" + file.getOriginalFilename();
		Path targetLocation = path.resolve(fileName);
		
		Files.copy(file.getInputStream(), targetLocation);
		return fileName;
	}

}
