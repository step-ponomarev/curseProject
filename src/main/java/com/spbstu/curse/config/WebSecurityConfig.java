package com.spbstu.curse.config;

import com.spbstu.curse.authorities.Role;
import com.spbstu.curse.authorities.User;
import com.spbstu.curse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  static final String DEFAULT_PASSWORD = "123";

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/user").not().authenticated()
        .antMatchers(HttpMethod.GET, "/user").permitAll()
        .antMatchers("/js/**", "/css/**", "/img/**", "/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .permitAll()
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

//    addAdmin();

    provider.setUserDetailsService(userService);
    provider.setPasswordEncoder(passwordEncoder);

    return provider;
  }

  private void addAdmin() {
    final var ADMIN = new User();
    ADMIN.setUsername("admin");
    ADMIN.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
    ADMIN.setRoles(Set.of(Role.ADMIN));

    userService.save(ADMIN);
  }
}
