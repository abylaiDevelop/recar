package kz.recar.services;

import kz.recar.model.Club;
import kz.recar.model.User;
import kz.recar.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClubService {
  private final ClubRepository clubRepository;
  private final UserServiceImpl userService;
  private final PostService postService;

  @Autowired
  public ClubService(ClubRepository clubRepository, UserServiceImpl userService, PostService postService) {
    this.clubRepository = clubRepository;
    this.userService = userService;
    this.postService = postService;
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
}
