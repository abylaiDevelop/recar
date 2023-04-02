package kz.recar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_photo")
@Getter
@Setter
public class Photo extends BaseEntity {
  public String path;
  public LocalDateTime createdAt;

}
