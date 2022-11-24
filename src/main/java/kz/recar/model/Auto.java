package kz.recar.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@Setter
@Getter
public class Auto {
    @Id
    private String id;
    @Indexed(unique = false)
    private String login;
    private String password;
    private String photo;
    private String description;
    private List<Post> posts;
    private List<Post> likedPosts;
    private List<Post> savedPosts;

}
