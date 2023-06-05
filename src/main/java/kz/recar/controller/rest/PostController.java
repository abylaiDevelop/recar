package kz.recar.controller.rest;

import kz.recar.model.Post;
import kz.recar.model.PostMedia;
import kz.recar.repository.PostRepository;
import kz.recar.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/post")
public class PostController {


  private PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/")
  public List<Post> getPosts() {
    return postService.getPosts();
  }

  @PostMapping("/add")
  public Post addPost(Post post, @RequestParam MultipartFile file, PostMedia postMedia) {
    return postService.createPost(post, file, postMedia);
  }

  @PostMapping("/add/test")
  public void addPostTest(@RequestBody Map<Object, Object> postMedia) {
    postService.test(postMedia);
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity<?> deletePost(@PathVariable Long id) throws Exception {
    boolean isDeleted = postService.delete(id);
    Map<Object, Object> response = new HashMap<>();

    if (!isDeleted) {
      throw new Exception("Can not find post");
    }

    response.put("message", "deleted");
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public Post getPost(@PathVariable Long id) {
    return postService.getPost(id);
  }

  @PostMapping("/like/{postId}")
  public ResponseEntity<?> likePost(@PathVariable Long postId) {
    Map<Object, Object> response = new HashMap<>();
    postService.reactionPost(postId, "like");
    response.put("message", "liked");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/save/{postId}")
  public ResponseEntity<?> savePost(@PathVariable Long postId) {
    Map<Object, Object> response = new HashMap<>();
    postService.reactionPost(postId, "save");
    response.put("message", "saved");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/update/{postId}")
  public ResponseEntity<?> updatePost(@PathVariable Long postId, Post post) {
    Post updatedPost = postService.update(post, postId);
    Map<Object, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "updated");
    response.put("post", updatedPost);

    return ResponseEntity.ok(response);
  }



}
