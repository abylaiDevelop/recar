package kz.recar.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "auto_model")
@Data
public class Model extends BaseEntity {
  private String modelName;

  @ManyToOne
  private Brand brand;
}
