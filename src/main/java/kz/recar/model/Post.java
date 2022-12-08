package kz.recar.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "t_posts")
@Getter
@Setter
public class Post {
    private LocalDateTime date;
    private String photos;
    private String description;

    @OneToOne
    private Auto author;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


}
