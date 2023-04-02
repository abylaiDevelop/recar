package kz.recar.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "follower")
@Data
public class Follower extends BaseEntity {
  @ManyToOne(fetch = FetchType.EAGER)
  private User followedUser;

  @ManyToOne(fetch = FetchType.EAGER)
  private User followingUser;
}
