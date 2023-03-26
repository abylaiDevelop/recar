package kz.recar.services;


import kz.recar.model.Auto;
import kz.recar.model.Permission;
import kz.recar.repository.AutoRepository;
import kz.recar.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutoServiceImpl implements AutoService {

  @Autowired
  private  AutoRepository repository;

  @Autowired
  private PermissionRepository permissionRepository;

  public Auto getById(Long id) {
    boolean check = repository.findById(id).isPresent();
    if (check) {
      return repository.findById(id).get();
    }
    return null;
  }


  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<Auto> getAutos() {
    return repository.findAll();
  }

  public Auto createAuto(Auto auto) {
    Auto check = repository.findByLogin(auto.getLogin());
    Permission permission = permissionRepository.findByName("ROLE_USER");
    List<Permission> permissions = new ArrayList<>();
    if (check == null) {
      auto.setPassword(passwordEncoder.encode(auto.getPassword()));
      if (permission != null) {
        permissions.add(permission);
        auto.setPermissions(permissions);
      } else {
        permission = new Permission();
        permission.setName("ROLE_USER");
        permissionRepository.save(permission);
        permission = permissionRepository.findByName("ROLE_USER");
        permissions.add(permission);
        auto.setPermissions(permissions);
      }
      repository.save(auto);
      return auto;
    } else {
      throw new IllegalArgumentException("auto already exists");
    }
  }

  public Auto updateAuto(Auto auto) {
    Auto check = repository.findByLogin(auto.getLogin());

    if (check != null) {
      boolean auth = check.getPassword().equals(auto.getPassword());
      if (auth) {
        check.setDescription(auto.getDescription());
        repository.save(check);
        return check;
      } else {
        throw new IllegalArgumentException("You dont have access to update");
      }

    } else {
      throw new IllegalArgumentException("Can not find auto");
    }
  }


  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    Auto auto = repository.findByLogin(login);
    if (auto == null) throw new UsernameNotFoundException("can not find auto");
    return auto;
  }
}
