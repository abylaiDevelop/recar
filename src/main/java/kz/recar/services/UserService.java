package kz.recar.services;

import kz.recar.model.User;
import kz.recar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Boolean addUser(User userData) {
        User checkUser = userRepository.findByLogin(userData.getLogin());

        if (checkUser == null) {
            User newUser = userRepository.save(userData);
            return newUser.getId() != null;
        }

        return false;
    }





}
