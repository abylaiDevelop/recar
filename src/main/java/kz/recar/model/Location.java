package kz.recar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_location")
@Getter
@Setter
public class Location extends BaseEntity {
  private String description;
  private Double lat;
  private Double lang;
}
