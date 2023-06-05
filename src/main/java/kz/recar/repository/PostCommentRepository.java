package kz.recar.repository;

import kz.recar.model.Post;
import kz.recar.model.PostComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
  List<PostComment> findByPost(Post post);

  List<PostComment> findAllByPost(Post post, Pageable pageable);
}
