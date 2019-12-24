package com.example.check_java_oop.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/login").permitAll()
      .antMatchers("/registration").permitAll()
      .antMatchers("/chat").permitAll()
      .antMatchers("/upload").hasRole("ADMIN")
      .antMatchers("/uploadExercise").hasRole("USER")
      .antMatchers("/admin/**").hasRole("ADMIN")
      .antMatchers("/exam/edit/**").hasRole("ADMIN")
      .antMatchers("/exam/delete/**").hasRole("ADMIN")
      .anyRequest()
        .authenticated()
      .and()
      .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/examList")
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll()
      .and()
      .logout()
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login")
        .permitAll()
      .and()
      .exceptionHandling()
        .accessDeniedPage("/access-denied");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
  }

}
