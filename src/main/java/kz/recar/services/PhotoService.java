package kz.recar.services;

import kz.recar.model.Photo;
import kz.recar.model.User;
import kz.recar.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;

@Service
public class PhotoService {
  public static String UPLOAD_DIRECTORY = "./uploads";

  private final PhotoRepository repository;

  private Path foundfile;

  @Autowired
  public PhotoService(PhotoRepository repository) {
    this.repository = repository;
  }

  public void init(Path root) {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  public Photo uploadPhoto(MultipartFile file) {
    String directoryName = "user_"+this.getCurrentUser().getId();
    Path root = Paths.get(UPLOAD_DIRECTORY, directoryName);
    try {
      this.init(root);
      long photoId = this.getLastId() + 1L;
      String fileCode = RandomStringUtils.randomAlphanumeric(8);
      String photoName = fileCode + "photo_" + photoId;
      Path fileNameAndPath = Paths.get(root.toString(), photoName+getExtensionByStringHandling(file.getOriginalFilename()).get());
      Files.copy(file.getInputStream(), fileNameAndPath);
      Photo photo = new Photo();
      photo.setPath(photoName);
      photo.setFileCode(fileCode);
      photo.setDowloadUri("/api/v1/file/download/" + fileCode);
      photo.setCreatedAt(LocalDateTime.now());
      return repository.save(photo);
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  public Photo getPhoto(Long id) {
    boolean check = repository.findById(id).isPresent();

    if (check) {
      return repository.findById(id).get();
    }
    return null;
  }

  public String getPath(Long id) {
    boolean check = repository.findById(id).isPresent();

    if (check) {
      Photo photo = repository.findById(id).get();
      return photo.getPath();
    }
    return null;
  }

  public Long getLastId() {
    Optional<Photo> userPhoto=repository.findTopByOrderByIdDesc();
    if (userPhoto.isPresent()){
      return userPhoto.get().getId();
    }
    else return -1L;
  }

  public Resource downloadFile(String filePath) throws IOException {
    String directoryName = "user_"+this.getCurrentUser().getId();
    Path dirPath = Paths.get(UPLOAD_DIRECTORY, directoryName);
    Files.list(dirPath).forEach(file -> {
      if (file.getFileName().toString().startsWith(filePath)) {
        this.foundfile = file;
      }
    });

    if (this.foundfile != null) {
      return new UrlResource(this.foundfile.toUri());
    }

    return null;

  }

  public Optional<String> getExtensionByStringHandling(String filename) {
    return Optional.ofNullable(filename)
            .filter(f -> f.contains("."))
            .map(f -> f.substring(filename.lastIndexOf(".")));
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)){
      return (User) authentication.getPrincipal();
    }
    return null;
  }
}
