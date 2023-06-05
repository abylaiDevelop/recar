package kz.recar.services;

import kz.recar.model.Post;
import kz.recar.model.PostComment;
import kz.recar.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
  private final PostCommentRepository commentRepository;
  private final PostService postService;
  private final UserServiceImpl userService;

  @Autowired
  public CommentService(PostCommentRepository commentRepository, PostService postService, UserServiceImpl userService) {
    this.commentRepository = commentRepository;
    this.postService = postService;
    this.userService = userService;
  }

  public List<PostComment> getComments(Long postId) {
    Post post = postService.getPost(postId);
    return commentRepository.findByPost(post);
  }

  public PostComment addComment(PostComment postComment, Long postId) {
    postComment.setAuthor(userService.getCurrentUser());
    postComment.setPost(postService.getPost(postId));
    postComment.setCreatedAt(LocalDateTime.now());
    return commentRepository.save(postComment);
  }

  public PostComment replyComment(PostComment postComment, Long commentId) {
    postComment.setAuthor(userService.getCurrentUser());
    postComment.setCreatedAt(LocalDateTime.now());
    PostComment replyTo = this.getComment(commentId);
    postComment.setReplyToComment(replyTo);
    return commentRepository.save(postComment);
  }

  public PostComment getComment(Long id) {
    boolean check = commentRepository.findById(id).isPresent();

    if (!check) {
      return null;
    }
    return commentRepository.findById(id).get();
  }

  public List<PostComment> getReplies(Long id) {
    PostComment postComment = this.getComment(id);
    return postComment.getReplies();
  }

  public void deleteComment(Long commentId) {
    commentRepository.deleteById(commentId);
  }

  public List<PostComment> getPageComments(Long postId, Integer page, Integer limit) {
    Pageable pageable = PageRequest.of(page, limit);
    Post post = postService.getPost(postId);
    return commentRepository.findAllByPost(post, pageable);
  }
}
