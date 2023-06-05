package kz.recar.controller.rest;

import kz.recar.model.Post;
import kz.recar.model.PostComment;
import kz.recar.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/{postId}")
  public List<PostComment> getComments(@PathVariable Long postId) {
    return commentService.getComments(postId);
  }

  @PostMapping("/add/{postId}")
  public PostComment addComment(PostComment postComment, @PathVariable Long postId) {
    return commentService.addComment(postComment, postId);
  }

  @PostMapping("/reply/{commentId}")
  public PostComment replyComment(PostComment postComment, @PathVariable Long commentId) {
    return commentService.replyComment(postComment, commentId);
  }

  @GetMapping("/replies/{commentId}")
  public List<PostComment> getReplies(@PathVariable Long commentId) {
    return commentService.getReplies(commentId);
  }

  @GetMapping("/delete{commentId}")
  public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
    commentService.deleteComment(commentId);
    Map<Object, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("message", "deleted");
    return ResponseEntity.ok(response);
  }

}
