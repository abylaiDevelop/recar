package kz.recar.controller.rest;

import kz.recar.model.User;
import kz.recar.services.PostService;
import kz.recar.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
  private final UserServiceImpl userService;
  private final PostService postService;

  public ProfileController(UserServiceImpl userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @GetMapping
  public ResponseEntity<?> getProfile() {
    User user = userService.getCurrentUser();
    return getResponseEntity(user);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
    User user = userService.getById(userId);
    return getResponseEntity(user);
  }

  private ResponseEntity<?> getResponseEntity(User user) {
    Map<Object, Object> response = new HashMap<>();
    response.put("user", user);
    response.put("followers", user.getFollowers());
    response.put("following", user.getFollowing());
    response.put("posts", user.getUserPosts());
    response.put("likes", user.getLikedPosts());
    response.put("saves", user.getSavedPosts());
    response.put("clubs", user.getClub());
    response.put("events", user.getUserEvents());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/post/delete/{postId}")
  public ResponseEntity<?> deletePost(@PathVariable Long postId) {
    postService.deletePost(postId);
    Map<Object, Object> response = new HashMap<>();
    response.put("message", "ok");
    return ResponseEntity.ok(response);
  }
}
