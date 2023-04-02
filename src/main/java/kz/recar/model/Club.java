package kz.recar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_clubs")
@Data
public class Club extends BaseEntity {
  @OneToOne
  private Brand brand;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> admins;

  private String name;

  private String description;

  @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Events> clubEvents;

  @OneToMany(mappedBy = "authorClub", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Post> posts;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<User> user;

  public void addUser(User user) {
    this.user.add(user);
  }

  public void removeUser(User user) {
    this.user.remove(user);
  }

}
