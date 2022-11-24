package kz.recar.services.ws;


import kz.recar.model.User;
import kz.recar.repository.UserRepository;


import javax.jws.WebService;

@WebService(
        name = "User",
        portName = "UserPort",
        targetNamespace = "http://service.ws.sample/",
        endpointInterface = "kz.recar.services.ws.WSUserService"
)
public class WSUserServiceImpl implements WSUserService {

    private final UserRepository userRepository;



    public WSUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String login) {
        User user = userRepository.findByLogin(login);
        if(user == null) return null;
        return user;
    }

    @Override
    public User createUser(String email, String name, String password) {
        User checkUser = userRepository.findByLogin(email);

        if (checkUser == null) {
            User user = new User();
            user.setUserName(name);
            user.setLogin(email);
            user.setPassword(password);
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
