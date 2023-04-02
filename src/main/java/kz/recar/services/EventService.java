package kz.recar.services;

import kz.recar.model.Events;
import kz.recar.model.Location;
import kz.recar.repository.EventRepository;
import kz.recar.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
  private final EventRepository eventRepository;
  private final ClubService clubService;
  private final UserServiceImpl userService;
  private final LocationRepository locationRepository;

  @Autowired
  public EventService(EventRepository eventRepository, ClubService clubService,
                      UserServiceImpl userService, LocationRepository locationRepository) {
    this.eventRepository = eventRepository;
    this.clubService = clubService;
    this.userService = userService;
    this.locationRepository = locationRepository;
  }

  public List<Events> getEvents() {
    return eventRepository.findAll();
  }

  public Events getEvent(Long id) {
    boolean check = eventRepository.findById(id).isPresent();

    if (!check)
      return null;

    return eventRepository.findById(id).get();
  }

  public void addMember(Long eventId) {
    Events events = this.getEvent(eventId);
    events.addMember(userService.getCurrentUser());
    eventRepository.save(events);
  }

  public void deleteMember(Long eventId) {
    Events events = this.getEvent(eventId);
    events.deleteMember(userService.getCurrentUser());
  }

  public Events createEvent(Events events, Location location, Long clubId) {
    events.setLocation(locationRepository.save(location));
    events.setAuthor(clubService.getClub(clubId));
    return eventRepository.save(events);
  }

  public void deleteEvent(Long id) {
    eventRepository.delete(this.getEvent(id));
  }

}
