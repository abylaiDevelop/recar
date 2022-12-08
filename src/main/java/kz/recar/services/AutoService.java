package kz.recar.services;

import kz.recar.model.Auto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AutoService extends UserDetailsService {
    Auto createAuto(Auto auto);
    Auto updateAuto(Auto auto);


}
