package com.example.check_java_oop.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private int id;
  @Column
  @NotEmpty(message = "*Please provide an username")
  private String username;
  @Column
  @Length(min = 6, message = "*Your password must have at least 6 characters")
  @NotEmpty(message = "*Please provide your password")
  private String password;
  @Column
  private boolean enabled;
  @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
  private ArrayList<UserClass> userClasses;
  @ManyToMany
  @JoinTable( 
    name = "user_role", 
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
  private ArrayList<Role> roles;

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

  public ArrayList<UserClass> getUserClasses() {
    return this.userClasses;
  }

  public void setUserClasses(ArrayList<UserClass> userClasses) {
    this.userClasses = userClasses;
  }

  public ArrayList<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(ArrayList<Role> roles) {
    this.roles = roles;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
