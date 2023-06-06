package kz.recar.repository;

import kz.recar.model.Follower;
import kz.recar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
  public Follower findFollowerByFollowedUserAndFollowingUser(User followedUser, User followingUser );
}
