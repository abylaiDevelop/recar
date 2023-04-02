package kz.recar.controller.rest;

import kz.recar.model.User;
import kz.recar.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
  private final UserServiceImpl userService;

  public ProfileController(UserServiceImpl userService) {
    this.userService = userService;
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
}
