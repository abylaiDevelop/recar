package kz.recar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post_media")
@Data
public class PostMedia extends BaseEntity {
  @Column(name = "media_file")
  private String mediaFile;
  private int position;
  private double longitude;
  private double latitude;

  @ManyToOne(fetch = FetchType.EAGER)
  @JsonIgnore
  private Post post;
}
