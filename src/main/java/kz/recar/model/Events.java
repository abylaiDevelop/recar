package kz.recar.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class Events {
    private Auto author;
    private LocalDateTime date;
    private String location;
    private String description;
    private String name;
    private List<Auto> members;

}
