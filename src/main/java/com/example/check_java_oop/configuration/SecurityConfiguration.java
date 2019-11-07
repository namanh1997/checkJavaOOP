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

  // @Autowired
  // private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Autowired
  private DataSource dataSource;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
  
  // @Override
  // protected void configure(AuthenticationManagerBuilder auth)
  //         throws Exception {
  //   auth
  //       .jdbcAuthentication()
  //       .usersByUsernameQuery(
  //         "select username,password,enabled from checkjavaoop.users where username = ?"
  //       )
  //       .authoritiesByUsernameQuery(
  //         "select user_id, role_id from checkjavaoop.user_roles where user_id=?"
  //       )
  //       .dataSource(dataSource)
  //       .passwordEncoder(bCryptPasswordEncoder)
  //       .withUser("user").password(bCryptPasswordEncoder.encode("123456"))
  //         .roles("USER")
  //       .and()
  //       .withUser("admin").password(bCryptPasswordEncoder.encode("123456"))
  //         .roles("ADMIN");
  // }

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
      .anyRequest()
        .authenticated()
      .and()
      .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/upload")
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
