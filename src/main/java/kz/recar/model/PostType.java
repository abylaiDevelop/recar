package kz.recar.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "post_type")
public class PostType extends BaseEntity {
  private String postType;
}
