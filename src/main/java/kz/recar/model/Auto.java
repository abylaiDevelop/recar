package kz.recar.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_auto")
@Getter
@Setter
public class Auto extends BaseEntity {
  private String year;

  private String color;

  private Double volume;

  private String plate_number;

  @OneToOne
  private Model model;
}
