package kz.recar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post_comments")
@Data
public class PostComment extends BaseEntity {
  @ManyToOne
  private User author;

  @ManyToOne
  @JsonIgnore
  private Post post;

  private LocalDateTime createdAt;

  private String comment;

  @ManyToOne
  private PostComment replyToComment;

  @OneToMany(mappedBy = "replyToComment", fetch = FetchType.EAGER)
  @JsonIgnore
  private List<PostComment> replies;
}
