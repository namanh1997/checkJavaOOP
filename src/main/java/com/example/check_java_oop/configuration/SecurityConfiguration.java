package com.example.check_java_oop.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  // @Autowired
  // private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private DataSource dataSource;

  // @Value("${spring.queries.users-query}")
  // private String usersQuery;

  // @Value("${spring.queries.roles-query}")
  // private String rolesQuery;
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
          throws Exception {
    auth
        .jdbcAuthentication()
        .usersByUsernameQuery(
          "select username,password,enabled from user where username = ?"
        )
        .authoritiesByUsernameQuery(
          "select user_id, role_id from user_role where user_id=?"
        )
        .dataSource(dataSource);
        // .passwordEncoder(bCryptPasswordEncoder);
  }

  // @Autowired
  // protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  //     auth.inMemoryAuthentication()
  //       .withUser("admin").password("123456").roles("USER", "ADMIN")
  //       .and()
  //       .withUser("user").password("123456").roles("USER");
  // }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .csrf()
          .disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/registration").permitAll()
        .antMatchers("/upload").permitAll()
        .antMatchers("/admin/**").hasAuthority("ADMIN")
        .anyRequest()
          .authenticated()
        .and()
        .formLogin()
          .loginPage("/login")
          .defaultSuccessUrl("/upload")
          .permitAll()
        .and()
        .logout()
          .invalidateHttpSession(true)
          .clearAuthentication(true)
          .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
          .logoutSuccessUrl("/")
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
