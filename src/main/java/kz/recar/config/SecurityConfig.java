package kz.recar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true, securedEnabled = true)
public class SecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/Service/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{



        http.exceptionHandling().accessDeniedPage("/403");

        http.authorizeRequests().antMatchers("/static/css/**", "/js/**","/Service/user").permitAll();
        http.formLogin()
                .loginProcessingUrl("/auth")
                .usernameParameter("user_email")
                .passwordParameter("user_password")
                .loginPage("/enter")
                .defaultSuccessUrl("/")
                .failureUrl("/enter?error");

        http.logout()
                .logoutSuccessUrl("/enter")
                .logoutUrl("/exit");

        http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/Service/user").permitAll();
        return http.build();
    }



}