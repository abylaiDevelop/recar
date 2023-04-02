package kz.recar.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_events")
@Getter
@Setter
public class Events extends BaseEntity {
    @ManyToOne
    private Club author;

    private String name;

    @OneToOne
    private Location location;

    private String description;

    @ManyToMany
    private List<User> members;

    public void addMember(User user) {
        this.members.add(user);
    }

    public void deleteMember(User user) {
        this.members.remove(user);
    }
}
