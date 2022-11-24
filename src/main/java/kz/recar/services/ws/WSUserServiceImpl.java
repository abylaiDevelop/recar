package kz.recar.services.ws;


import kz.recar.model.User;
import kz.recar.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.jws.WebService;

@WebService(
        name = "User",
        portName = "UserPort",
        targetNamespace = "http://service.ws.sample/",
        endpointInterface = "kz.recar.services.ws.WSUserService"
)
public class WSUserServiceImpl implements WSUserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public WSUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String login) {
        User user = userRepository.findByLogin(login);
        if(user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }

    @Override
    public User createUser(String email, String name, String password) {
        User checkUser = userRepository.findByLogin(email);

        if (checkUser == null) {
            User user = new User();
            user.setUserName(name);
            user.setLogin(email);
            user.setPassword(passwordEncoder.encode(password));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User updateUser(String email, String name) {
        User checkUser = userRepository.findByLogin(email);

        if (checkUser != null) {
            checkUser.setUserName(name);
            userRepository.save(checkUser);
            return checkUser;
        }
        return null;
    }

    @Override
    public String deleteUser(String login) {
        User checkUser = userRepository.findByLogin(login);

        if (checkUser != null) {
            userRepository.delete(checkUser);
            return "User " + login + ": Deleted";
        }
        return "Can not find user";
    }
}
