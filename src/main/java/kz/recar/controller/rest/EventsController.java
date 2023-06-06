package kz.recar.controller.rest;

import kz.recar.model.Events;
import kz.recar.model.Location;
import kz.recar.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {
  private final EventService eventService;

  public EventsController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping
  public List<Events> getEvents() {
    return eventService.getEvents();
  }

  @GetMapping("/{eventId]")
  public Events getEvent(@PathVariable Long eventId) {
    return eventService.getEvent(eventId);
  }


  @PostMapping("/create/{clubId}")
  public Events createEvent(Events event, Location location, @PathVariable Long clubId) {
    return eventService.createEvent(event,location, clubId);
  }

  @PostMapping("/assign/{eventId}")
  public ResponseEntity<?> assignMember(@PathVariable Long eventId) {
    eventService.addMember(eventId);
    return ResponseEntity.ok("success");
  }

  @PostMapping("/delete/{eventId}")
  public ResponseEntity<?> deleteMember(@PathVariable Long eventId) {
    eventService.deleteMember(eventId);
    return ResponseEntity.ok("success");
  }

}
