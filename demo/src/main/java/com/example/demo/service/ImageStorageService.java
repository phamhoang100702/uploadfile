package com.example.demo.service;

import com.example.demo.serviceI.IStorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements IStorageService {

    private final Path storePath = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storePath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialize storage ", e);

        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("haahss");
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }
            if (!isImageFile(file)) {
                throw new RuntimeException("You can only upload file image");
            }
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 0.5f) {
                throw new RuntimeException("File must be <= 5mb");
            }

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFile = UUID.randomUUID().toString().replace("-", "");
            generatedFile += "." + fileExtension;
            Path destinationFilePath = this.storePath.resolve(
                    Paths.get(generatedFile)).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storePath.toAbsolutePath())) {
                throw new RuntimeException(
                        "Cannot store file outside current directory"
                );
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storePath,1).
                    filter(path ->{
                        return !path.equals(this.storePath) && path.toString().contains(".") ;})
                    .map(this.storePath::relativize);
        }catch (IOException e){
            throw new RuntimeException("Failed to load store files.",e);
        }

    }

    @Override
    public byte[] readFileContent(String filename) {
        try{
            Path file = storePath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file" + filename
                );
            }
        }catch(IOException e){
            throw new RuntimeException("Count not run file" + filename);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
