package com.ultimate.controller;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.ultimate.model.FileResponse;
import com.ultimate.service.IFileSytemStorage;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileController {

    @Autowired
    IFileSytemStorage fileSytemStorage;

    @PostMapping(value = "/uploadfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileResponse> uploadSingleFile(@RequestPart(value = "uploadfile", required = true) MultipartFile file) {
        String upfile = fileSytemStorage.saveFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(upfile)
                .toUriString();
        return ResponseEntity.status(HttpStatus.OK).body(new FileResponse(upfile, fileDownloadUri, "File uploaded with success!"));
    }

    @PostMapping(value = "/uploadfiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileResponse>> uploadMultipleFiles(@RequestPart(value = "uploadfiles", required = true) MultipartFile[] files) {
        List<FileResponse> responses = Arrays.asList(files)
                .stream()
                .map(file -> {
                    String upfile = fileSytemStorage.saveFile(file);
                    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/download/")
                            .path(upfile)
                            .toUriString();
                    return new FileResponse(upfile, fileDownloadUri, "File uploaded with success!");
                })
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {

        Resource resource = fileSytemStorage.loadFile(filename);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.print("Could not determine file type.");
        }
        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/list")
    public List<String> listAllFiles() {
        List<String> list = null;
        File fileDir = new File("D:\\UploadedFiles");
        if (fileDir.isDirectory()) {
            String files[] = fileDir.list();
            list = Arrays.asList(files);
        }
        return list;
    }
}
