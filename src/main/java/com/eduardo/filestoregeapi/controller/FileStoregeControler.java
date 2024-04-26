package com.eduardo.filestoregeapi.controller;

import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardo.filestoregeapi.FileStoregeProperties;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/file")
public class FileStoregeControler {
    private final Path fileStorageLocation;

    public FileStoregeControler(FileStoregeProperties fileStoregeProperties) {
        this.fileStorageLocation = Path.of(fileStoregeProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation.toFile());
            
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/download/")
                .path(fileName).toUriString();

            return ResponseEntity.ok("File uploaded successfully with URL: " + fileDownloadUri);
        }catch(Exception e){
           return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<UrlResource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException{
        Path filePath = fileStorageLocation.resolve(fileName).normalize();
        
        try{
            UrlResource resource = new UrlResource(filePath.toUri());
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if(contentType == null){
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+resource.getFilename()+"\"")
            .body(resource);

        }catch(MalformedURLException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws IOException{
        List <String> fileNames = Files.list(fileStorageLocation)
        .map(Path::getFileName)
        .map(Path::toString)
        .collect(Collectors.toList());

        return ResponseEntity.ok(fileNames);
    }
}
