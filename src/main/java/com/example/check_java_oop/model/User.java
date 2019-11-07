package com.example.check_java_oop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private int id;
  @Column(name = "username")
  @Length(min = 6, message = "*Your username must have at least 6 characters")
  @NotEmpty(message = "*Please provide an username")
  private String username;
  @Column(name = "password")
  @Length(min = 6, message = "*Your password must have at least 6 characters")
  @NotEmpty(message = "*Please provide your password")
  private String password;
  @Column(name = "admin")
  @NotNull
  @AssertFalse
  private Boolean admin;
  @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
  private Set<UserClass> userClasses;

  public User() {}

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean isAdmin() {
    return this.admin;
  }

  public Boolean getAdmin() {
    return this.admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public Set<UserClass> getUserClasses() {
    return this.userClasses;
  }

  public void setUserClasses(Set<UserClass> userClasses) {
    this.userClasses = userClasses;
  }

}
