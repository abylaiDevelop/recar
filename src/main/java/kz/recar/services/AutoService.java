package kz.recar.services;


import kz.recar.model.Auto;
import kz.recar.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    @Autowired
    private  AutoRepository repository;

    public List<Auto> getAutos() {
        return repository.findAll();
    }

    public Auto createAuto(Auto auto) {
        Auto check = repository.findByLogin(auto.getLogin());

        if (check == null) {
            repository.insert(auto);
            return repository.findByLogin(auto.getLogin());
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



}
