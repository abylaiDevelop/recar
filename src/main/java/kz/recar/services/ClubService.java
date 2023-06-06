package kz.recar.services;

import kz.recar.model.*;
import kz.recar.repository.ClubRepository;
import kz.recar.repository.PostMediaRepository;
import kz.recar.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClubService {
  private final ClubRepository clubRepository;
  private final UserServiceImpl userService;
  private final PostService postService;
  private final PhotoService photoService;
  private final PostMediaRepository postMediaRepository;
  private final PostRepository postRepository;

  @Autowired
  public ClubService(ClubRepository clubRepository, UserServiceImpl userService, PostService postService, PhotoService photoService, PostMediaRepository postMediaRepository, PostRepository postRepository) {
    this.clubRepository = clubRepository;
    this.userService = userService;
    this.postService = postService;
    this.photoService = photoService;
    this.postMediaRepository = postMediaRepository;
    this.postRepository = postRepository;
  }

  public List<Club> getClubs() {
    return clubRepository.findAll();
  }

  public Club createClub(Club club) {
    List<User> admins = new ArrayList<>();
    User user = userService.getCurrentUser();
    admins.add(user);
    club.setAdmins(admins);
    return clubRepository.save(club);
  }

  public Club assignAdmin(Long clubId, Long userId) {
    Club club = this.getClub(clubId);
    List<User> admins = club.getAdmins();
    User user = userService.getById(userId);
    admins.add(user);
    club.setAdmins(admins);
    return clubRepository.save(club);
  }

  public Club subscribe(Long id) {
    Club club = this.getClub(id);
    club.addUser(userService.getCurrentUser());
    return clubRepository.save(club);
  }

  public void unsubscribe(Long id) {
    Club club = this.getClub(id);
    club.removeUser(userService.getCurrentUser());
  }

  public Club getClub(Long id) {
    boolean check = clubRepository.findById(id).isPresent();

    if (!check) {
      return null;
    }

    return clubRepository.findById(id).get();
  }

  public void deleteClub(Long id) {
    clubRepository.delete(this.getClub(id));
  }


  public Post createPost(Post post, Long clubId, MultipartFile file, PostMedia postMedia) {
    Club club = this.getClub(clubId);
    Photo filepath = photoService.uploadPhoto(file);
    postMedia.setMediaFile(filepath.getDowloadUri());
    List<Post> posts = club.getPosts();
    posts.add(post);

    Post newPost = postRepository.save(post);
    newPost.setCreatedAt(LocalDateTime.now());
    postMedia.setPost(newPost);
    return postMediaRepository.save(postMedia).getPost();
  }
}
