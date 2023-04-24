package kz.recar.services;

import kz.recar.model.Photo;
import kz.recar.model.Post;
import kz.recar.model.PostMedia;
import kz.recar.model.User;
import kz.recar.repository.PhotoRepository;
import kz.recar.repository.PostMediaRepository;
import kz.recar.repository.PostRepository;
import kz.recar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {
  private final PostRepository postRepository;
  private final UserServiceImpl userService;
  private final PhotoService photoService;
  private final PostMediaRepository postMediaRepository;
  private final UserRepository userRepository;

  @Autowired
  public PostService(PostRepository postRepository, UserServiceImpl userService, PhotoService photoService,
                     PostMediaRepository postMediaRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userService = userService;
    this.photoService = photoService;
    this.postMediaRepository = postMediaRepository;
    this.userRepository = userRepository;
  }

  public List<Post> getPosts() {
    return postRepository.findAll();
  }

  public Post createPost(Post post, MultipartFile file, PostMedia postMedia) {
    User currentUser = userService.getCurrentUser();
    Photo filepath = photoService.uploadPhoto(file);
    postMedia.setMediaFile(filepath.getPath());
    post.setAuthor(currentUser);
    Post newPost = postRepository.save(post);
    newPost.setCreatedAt(LocalDateTime.now());
    postMedia.setPost(newPost);
    return postMediaRepository.save(postMedia).getPost();
  }


  public boolean delete(Long id) {
    boolean check = postRepository.findById(id).isPresent();

    if (check) {
      postRepository.delete(postRepository.findById(id).get());
      return true;
    } else {
      return false;
    }
  }

  public Post getPost(Long id) {
    boolean check = postRepository.findById(id).isPresent();

    if (!check) {
      return null;
    }

    return postRepository.findById(id).get();
  }

  public void reactionPost(Long id, String action) {
    List<User> userList = new ArrayList<>();
    Post post = this.getPost(id);
    User user = userService.getCurrentUser();
    userList.add(user);
    switch (action) {
      case "like" :
        post.setReactionToPost(userList);
        break;
      case "save":
        List<Post> savedPosts = user.getSavedPosts();
        savedPosts.add(post);
        user.setSavedPosts(savedPosts);
        userRepository.save(user);
        break;
    }
    postRepository.save(post);
  }

  public Post test(Map<Object, Object> json) {
    String caption = (String) json.get("caption");
    Post post = new Post();
    post.setCaption(caption);
    post = postRepository.save(post);
    ArrayList<Object> postMedia = (ArrayList<Object>) json.get("mediaPosts");
    for (Object data: postMedia) {
      HashMap<Object, Object> hashMap = (HashMap<Object, Object>) data;
      Long position = (Long) hashMap.get("position");
      String file = (String) hashMap.get("file");
    }
    return null;
  }
}
