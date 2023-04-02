package kz.recar.controller.rest;

import kz.recar.dto.AuthenticationRequestDto;
import kz.recar.dto.UserDto;
import kz.recar.model.User;
import kz.recar.security.JwtTokenProvider;
import kz.recar.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {

  private final JwtTokenProvider jwtTokenProvider;

  private final UserServiceImpl userService;

  @Autowired
  public AuthController(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
    try {
      String username = requestDto.getUsername();
      User user = userService.loadUserByUsername(username);

      String token = jwtTokenProvider.createToken(username, user.getPermissions());

      Map<Object, Object> response = new HashMap<>();
      response.put("username", username);
      response.put("token", token);

      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  @PostMapping("/register")
  public ResponseEntity register(UserDto userDto) {
    User user = userService.createUser(userDto);

    String token = jwtTokenProvider.createToken(user.getLogin(), user.getPermissions());

    Map<Object, Object> response = new HashMap<>();
    response.put("status", "success");
    response.put("token", token);

    return ResponseEntity.ok(response);
  }
}
