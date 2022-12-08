package kz.recar;

import kz.recar.model.Auto;
import kz.recar.model.Permission;
import kz.recar.repository.AutoRepository;
import kz.recar.repository.PermissionRepository;
import kz.recar.services.AutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class RecarApplication {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(RecarApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AutoRepository repository, PermissionRepository permissionRepository) {
        return args -> {

            Permission check = permissionRepository.findByName("ROLE_ADMIN");

            if (check == null) {
                Auto user = new Auto();
                user.setLogin("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                Permission permission = new Permission();
                permission.setName("ROLE_ADMIN");
                List<Permission> permissions = new ArrayList<>();
                permissions.add(permissionRepository.save(permission));
                user.setPermissions(permissions);
                repository.save(user);
            } else {
                System.out.println("Admin exist");
            }

            AutoServiceImpl service = new AutoServiceImpl();
        };
    }
}
