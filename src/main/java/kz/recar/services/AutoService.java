package kz.recar.services;


import kz.recar.model.Auto;
import kz.recar.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService implements UserDetailsService {

    @Autowired
    private  AutoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Auto> getAutos() {
        return repository.findAll();
    }

    public Auto createAuto(Auto auto) {
        Auto check = repository.findByLogin(auto.getLogin());

        if (check == null) {
            auto.setPassword(passwordEncoder.encode(auto.getPassword()));
            repository.insert(auto);
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auto auto = repository.findByLogin(username);
        if (auto == null) throw new UsernameNotFoundException("can not find auto");
        return auto;
    }
}
