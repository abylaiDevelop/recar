package kz.recar;

import kz.recar.model.Auto;
import kz.recar.model.Post;
import kz.recar.repository.AutoRepository;
import kz.recar.services.AutoService;
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
//
//            Auto audi = new Auto();
//            audi.setLogin("audi");
//            audi.setPassword("audi");
//            audi.setDescription("new audi");
//
//            AutoService service = new AutoService();
//            service.createAuto(audi);
//
//
//        };
//    }
}
