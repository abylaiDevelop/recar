package kz.recar.services;

import kz.recar.dto.UserDto;
import kz.recar.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User createUser(UserDto user);
    User updateUser(User user);


}
