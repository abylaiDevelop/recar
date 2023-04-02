package kz.recar.controller.rest;

import kz.recar.model.Club;
import kz.recar.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/club")
public class ClubController {
  private final ClubService clubService;

  @Autowired
  public ClubController(ClubService clubService) {
    this.clubService = clubService;
  }

  @GetMapping
  public List<Club> getClubs() {
    return clubService.getClubs();
  }

  @GetMapping("/{clubId}")
  public Club getClub(@PathVariable Long clubId) {
    return clubService.getClub(clubId);
  }

  @PostMapping("/subscribe/{clubId}")
  public Club subscribeClub(@PathVariable Long clubId) {
    return clubService.subscribe(clubId);
  }

  @PostMapping("/unsubscribe/{clubId}")
  public ResponseEntity<?> unsubscribe(@PathVariable Long clubId) {
    clubService.unsubscribe(clubId);
    Map<Object, Object> response = new HashMap<>();
    response.put("message", "unsubscribed");
    return ResponseEntity.ok(response);
  }

  @PostMapping("/create")
  public Club createClub(Club club) {
    return clubService.createClub(club);
  }

  @PostMapping("/assign/admin/{clubId}/{userId}")
  public Club assignAdmin(@PathVariable Long clubId, @PathVariable Long userId) {
    return clubService.assignAdmin(clubId, userId);
  }
}
