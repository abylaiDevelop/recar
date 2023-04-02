package kz.recar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_post")
@Getter
@Setter
public class Post extends BaseEntity {
    private String description;

    @ManyToOne
    private User author;

    @ManyToOne
    private Club authorClub;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    private List<PostComment> postComments;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "caption")
    private String caption;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> reactionToPost;

    @ManyToMany(mappedBy = "savedPosts", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> savePostUsers;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private List<PostMedia> postMedia;

    @OneToOne
    private PostType postType;

}
