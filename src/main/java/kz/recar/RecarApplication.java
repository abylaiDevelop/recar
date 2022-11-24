package kz.recar;

import kz.recar.model.Auto;
import kz.recar.model.Post;
import kz.recar.repository.AutoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class RecarApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecarApplication.class, args);
    }

//    @Bean
//    CommandLineRunner runner(AutoRepository repository) {
//        return args -> {
//
//            Post post = new Post(
//                    LocalDateTime.now(),
//                    "",
//                    "new Audi"
//            );
//            List<Post> posts = new ArrayList<>();
//            posts.add(post);
//
//            Auto audi = new Auto(
//                    "abyl",
//                    "password",
//                    "",
//                    "Audi portfolio",
//                    posts
//            );
//
//            Auto check = repository.findByLogin("abyl");
//            if (check == null) {
//                repository.insert(audi);
//            } else {
//                System.out.println("Auto exists");
//            }
//
//        };
//    }
}
