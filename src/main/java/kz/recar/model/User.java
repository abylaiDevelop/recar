package kz.recar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_user")
@Getter
@Setter
public class User extends BaseEntity implements UserDetails {
  @Column(unique = true)
  private String login;

  @JsonIgnore
  private String password;

  @Column(unique = true)
  private String email;
  private String fullName;

  @ManyToMany(mappedBy = "admins", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Club> adminClub;

  @ManyToMany(fetch = FetchType.EAGER)
  @Fetch(FetchMode.SELECT)
  @JsonIgnore
  private List<Permission> permissions;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "reactionToPost")
  @Fetch(FetchMode.SELECT)
  @JsonIgnore
  private List<Post> likedPosts;

  @ManyToMany(fetch = FetchType.EAGER)
  @Fetch(FetchMode.SELECT)
  @JsonIgnore
  private List<Post> savedPosts;

  @OneToOne(cascade = CascadeType.ALL)
  private Photo avatar;

  @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Post> userPosts;

  @ManyToMany(fetch = FetchType.EAGER)
  private List<Auto> userAuto;

  @JsonIgnore
  @OneToMany(mappedBy = "followingUser", fetch = FetchType.EAGER)
  private List<Follower> followers;

  @JsonIgnore
  @OneToMany(mappedBy = "followedUser", fetch = FetchType.EAGER)
  private List<Follower> following;

  @JsonIgnore
  @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
  private List<Events> userEvents;

  @JsonIgnore
  @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
  private List<PostComment> comments;

  @ManyToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<Club> club;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return permissions;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return login;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
