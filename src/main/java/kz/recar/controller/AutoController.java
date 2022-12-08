package kz.recar.controller;

import io.swagger.annotations.Api;
import kz.recar.model.Auto;
import kz.recar.services.AutoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("api/v1/autos")
@AllArgsConstructor
@Api(value = "User Resource REST Endpoint", description = "Shows the user info")
public class AutoController {

    private final AutoServiceImpl autoServiceImpl;

    @GetMapping
    public List<Auto> getAutos() {
        return autoServiceImpl.getAutos();
    }

    @PostMapping("/add")
    public Auto createAuto(@RequestBody Auto auto) {
        return autoServiceImpl.createAuto(auto);
    }

    @PostMapping("/update")
    public Auto updateAuto(@RequestBody Auto auto) {
        return autoServiceImpl.updateAuto(auto);
    }




}
