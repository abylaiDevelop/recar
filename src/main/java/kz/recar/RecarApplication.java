package kz.recar;

import kz.recar.model.Permission;
import kz.recar.model.User;
import kz.recar.repository.UserRepository;
import kz.recar.repository.PermissionRepository;
import kz.recar.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class RecarApplication {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public static void main(String[] args) {
    SpringApplication.run(RecarApplication.class, args);
  }

  @Bean
  CommandLineRunner runner(UserRepository repository, PermissionRepository permissionRepository) {
    return args -> {

      Permission check = permissionRepository.findByName("ROLE_ADMIN");

      if (check == null) {
        User user = new User();
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

      UserServiceImpl service = new UserServiceImpl();
    };
  }
}
