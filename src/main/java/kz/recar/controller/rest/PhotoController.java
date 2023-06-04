package kz.recar.controller.rest;

import kz.recar.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/file")
public class PhotoController {
  @Autowired
  private PhotoService photoService;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
    try {
      photoService.uploadPhoto(file);
      return ResponseEntity.ok("File uploaded");
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  @GetMapping("/download/{filePath:.+}")
  public ResponseEntity<Resource> downloadPhoto(@PathVariable String filePath, HttpServletRequest request) throws Exception {

    Resource resource = photoService.downloadFile(filePath);

    String contentType = null;

    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {

      System.out.println("Could not determine file type.");
    }

    if(contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);

  }



}
