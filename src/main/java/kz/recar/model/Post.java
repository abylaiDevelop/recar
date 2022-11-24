package kz.recar.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_post")
@Getter
@Setter
public class Post extends BaseEntity {

    @Column(name = "description")
    private String description;
    @Column(name = "date")
    private Date date;
    @Column(name = "likes")
    private int likes;
    @Column(name = "views")
    private int views;
    @Column(name = "photo_urls")
    private String photoUrls;


}
