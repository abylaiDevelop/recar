package kz.recar.model;

import lombok.Data;

import java.util.List;

@Data
public class Club {
    private List<Auto> admins;
    private List<Auto> subscribers;
    private List<Post> posts;
    private String name;
    private String description;
    private String photo;
    private List<Events> events;

}
