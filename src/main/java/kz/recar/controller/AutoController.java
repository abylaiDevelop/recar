package kz.recar.controller;

import kz.recar.model.Auto;
import kz.recar.services.AutoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("api/v1/autos")
@AllArgsConstructor
public class AutoController {

    private final AutoServiceImpl autoServiceImpl;

    @GetMapping
    public List<Auto> getAutos() {
        return autoServiceImpl.getAutos();
    }

    @PostMapping("/add")
    public Auto createAuto(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String description = request.getParameter("description");

        Auto auto = new Auto();
        auto.setLogin(login);
        auto.setDescription(description);
        auto.setPassword(password);

        return autoServiceImpl.createAuto(auto);
    }

    @PostMapping("/update")
    public Auto updateAuto(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String description = request.getParameter("description");

        Auto auto = new Auto();

        auto.setPassword(password);
        auto.setLogin(login);
        auto.setDescription(description);

        return autoServiceImpl.updateAuto(auto);
    }


}
