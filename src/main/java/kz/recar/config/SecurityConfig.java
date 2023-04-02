package kz.recar.config;

import kz.recar.security.JwtConfigurer;
import kz.recar.security.JwtTokenProvider;
import kz.recar.services.UserService;
import kz.recar.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class SecurityConfig {

  private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
  private static final String LOGIN_ENDPOINT = "/api/v1/auth/**";

  private final JwtTokenProvider jwtTokenProvider;

  @Bean
  public UserService autoService() {return new UserServiceImpl();}

  @Autowired
  public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder builder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
    builder.userDetailsService(autoService());

    http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(LOGIN_ENDPOINT).permitAll()
            .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));

    return http.build();
  }


}
