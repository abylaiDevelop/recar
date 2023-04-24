package kz.recar.services;


import kz.recar.dto.UserDto;
import kz.recar.model.*;
import kz.recar.repository.FollowerRepository;
import kz.recar.repository.UserRepository;
import kz.recar.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private  UserRepository repository;

  @Autowired
  private PhotoService photoService;

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private FollowerRepository followerRepository;

  public User getById(Long id) {
    boolean check = repository.findById(id).isPresent();
    if (check) {
      return repository.findById(id).get();
    }
    return null;
  }

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public boolean authorize(String login, String password) {
    User user = this.loadUserByUsername(login);
    password = passwordEncoder.encode(password);
    return user.getLogin().equals(login) && password.equals(user.getPassword());
  }

  public List<User> getUsers() {
    return repository.findAll();
  }

  public User createUser(UserDto userDto) {
    User check = repository.findByLogin(userDto.getLogin());
    Permission permission = permissionRepository.findByName("USER");
    List<Permission> permissions = new ArrayList<>();
    User user = new User();
    if (check == null) {
      user.setPassword(passwordEncoder.encode(userDto.getPassword()));
      if (permission == null) {
        permission = new Permission();
        permission.setName("USER");
        permissionRepository.save(permission);
        permission = permissionRepository.findByName("USER");
      }
      permissions.add(permission);
      user.setLogin(userDto.getLogin());
      user.setEmail(userDto.getEmail());
      user.setPermissions(permissions);
      user.setFullName(userDto.getFullName());
      repository.save(user);
      return user;
    } else {
      throw new IllegalArgumentException("auto already exists");
    }
  }

  public User updateUser(User auto) {
    User check = repository.findByLogin(auto.getLogin());

  return null;
  }


  public User loadUserByUsername(String login) throws UsernameNotFoundException {
    User auto = repository.findByLogin(login);
    if (auto == null) throw new UsernameNotFoundException("can not find auto");
    return auto;
  }

  public Boolean deleteUser(Long id) {
    boolean check = repository.findById(id).isPresent();

    if (check) {
      repository.deleteById(id);
      return true;
    }
    return false;
  }

  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)){
      return (User) authentication.getPrincipal();
    }
    return null;
  }

  public void uploadAvatar(MultipartFile file) {
    Photo photo = photoService.uploadPhoto(file);
    User user = getCurrentUser();
    user.setAvatar(photo);
    repository.save(user);
  }

  public Follower followUser(Long id) {
    Follower follower = new Follower();
    User user = this.getCurrentUser();
    User follow = this.getById(id);
    System.out.println("User to follow" + follow);
    follower.setFollowedUser(user);
    follower.setFollowingUser(follow);
    Follower newFollower = followerRepository.save(follower);
    return follower;
  }

  public List<Follower> getFollowers() {
    User user = this.getCurrentUser();

    return user.getFollowers();
  }

  public List<Follower> getFollowing() {
    User user = this.getCurrentUser();
    return user.getFollowing();
  }

  public List<User> getUserFollowerById(Long id) {
    User user = this.getById(id);
    List<User> followers = new ArrayList<>();
    List<Follower> followerList = user.getFollowers();

    for (Follower follower: followerList) {
      User fUser = follower.getFollowedUser();
      followers.add(fUser);
    }

    return followers;
  }

  public List<Follower> getUserFollowingById(Long id) {
    User user = this.getById(id);
    return user.getFollowing();
  }

  public List<Post> likedPosts() {
    User user = getCurrentUser();
    return user.getLikedPosts();
  }

  public List<Post> savedPosts() {
    User user = getCurrentUser();
    return user.getSavedPosts();
  }
}
