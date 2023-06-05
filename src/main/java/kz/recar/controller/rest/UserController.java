package kz.recar.controller.rest;

import kz.recar.model.Follower;
import kz.recar.model.Photo;
import kz.recar.model.Post;
import kz.recar.model.User;
import kz.recar.repository.UserRepository;
import kz.recar.services.PhotoService;
import kz.recar.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
  @Autowired
  private UserServiceImpl userService;

  @Autowired
  private PhotoService photoService;

  @GetMapping("/")
  public List<User> getUsers(){
    return userService.getUsers();
  }

  public User updateUser(User user) {
    return userService.updateUser(user);
  }

  public void removeUser(Long id) {
    boolean check = userService.deleteUser(id);
  }

  @PostMapping("/upload/avatar")
  public ResponseEntity<?> uploadAvatar(@RequestParam MultipartFile file) {
    userService.uploadAvatar(file);
    Map<Object, Object> response = new HashMap<>();
    response.put("message", "Uploaded");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/upload/background")
  public Photo uploadBackground(@RequestParam MultipartFile file) {
    return userService.uploadBackground(file);
  }

  @PostMapping("/follow")
  public Follower followUser(Long userId) {
    return userService.followUser(userId);
  }

  @GetMapping("/followers")
  public List<Follower> getFollowers() {
    return userService.getFollowers();
  }

  @GetMapping("/followers/{userId}")
  public List<User> getFollowersById(@PathVariable Long userId) {
    return userService.getUserFollowerById(userId);
  }

  @GetMapping("/following/{userId}")
  public List<Follower> getFollowingByID(@PathVariable Long userId) {
    return userService.getUserFollowingById(userId);
  }

  @GetMapping("/likedPosts")
  public List<Post> likedPosts() {
    return userService.likedPosts();
  }

  @GetMapping("/savedPosts")
  public List<Post> savedPosts() {
    return userService.savedPosts();
  }


}
