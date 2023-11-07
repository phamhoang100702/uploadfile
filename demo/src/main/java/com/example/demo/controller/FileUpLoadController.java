package com.example.demo.controller;

import com.example.demo.model.ResponseObject;
import com.example.demo.serviceI.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.MvcNamespaceHandler;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/v1/FileUpLoad")
public class FileUpLoadController {

    @Autowired
    private IStorageService storageService;

    @PostMapping("")
    public ResponseEntity<ResponseObject>  uploadFile(@RequestParam("file")MultipartFile file){
        try {
            String generatedFileName = storageService.storeFile(file);
            return  ResponseEntity.ok().body(
                    new ResponseObject("ok","upload file successfully",generatedFileName)
            );
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed",e.getMessage(),"")
            );
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String filename){
        try {
            byte[] bytes =storageService.readFileContent(filename);
            return  ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        }catch (Exception e){
            return  ResponseEntity.noContent().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getUploadFile(){
        try {
            List<String> url = storageService.loadAll()
                    .map(path->{
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUpLoadController.class,
                                "readDetailFile",path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(
                    new ResponseObject("ok","List file successfully",url)
            );
        }catch (Exception e){
            throw new RuntimeException("Failed to get file ", e);
        }
    }
}
