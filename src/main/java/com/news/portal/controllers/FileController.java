package com.news.portal.controllers;


import com.news.portal.dto.FileResponseDTO;
import com.news.portal.services.UserService;
import com.news.portal.services.files.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final UserService userService;

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("avatar") MultipartFile file,
                                          Principal principal) {
        String name = storageService.store(file);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("./storage")
                .path(name)
                .toUriString();
        String username = principal.getName();
        userService.loadAvatar(username, uri);
        return ResponseEntity.ok().body(username + ", The avatar is loaded.");
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String name = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download")
                .path(name)
                .toUriString();

        return ResponseEntity.ok().body(new FileResponseDTO(name, uri, file.getContentType(), file.getSize()));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PostMapping("/upload-multiple-files")
    @ResponseBody
    public List<ResponseEntity<?>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/download/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> downloadFile(@PathVariable String filename) throws Exception {
        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        resource.getFilename() + "\"")
                .body(resource);
    }
}
