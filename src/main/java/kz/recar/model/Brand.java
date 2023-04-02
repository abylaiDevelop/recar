package kz.recar.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "auto_brand")
@Data
public class Brand extends BaseEntity {
  private String brand_name;
}
