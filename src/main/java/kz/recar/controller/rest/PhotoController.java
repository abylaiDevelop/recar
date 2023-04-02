package kz.recar.controller.rest;

import kz.recar.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/admin/file")
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

}
