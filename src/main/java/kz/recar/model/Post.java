package kz.recar.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class Post {
    private LocalDateTime date;
    private String photos;
    private String description;
    private Auto author;


}
