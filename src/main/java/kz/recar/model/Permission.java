package kz.recar.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "t_permissions")
@Getter
@Setter
public class Permission extends BaseEntity implements GrantedAuthority {
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }


}
