package com.example.check_java_oop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "user_class")
public class UserClass {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_class_id")
  private int id;
  @Column(name = "name")
  @NotNull
  private String name;
  @Column(name = "modifier")
  @NotNull
  private String modifier;
  @Column(name = "extend")
  private String extend;
  @Column(name = "implement")
  private String implement;
  @OneToMany(mappedBy="user_variable", cascade = CascadeType.ALL)
  private Set<UserVariable> userVariables;
  @OneToMany(mappedBy="user_method", cascade = CascadeType.ALL)
  private Set<UserMethod> userMethods;
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public UserClass() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModifier() {
    return this.modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public String getExtend() {
    return this.extend;
  }

  public void setExtend(String extend) {
    this.extend = extend;
  }

  public String getImplement() {
    return this.implement;
  }

  public void setImplement(String implement) {
    this.implement = implement;
  }

  public Set<UserVariable> getUserVariables() {
    return this.userVariables;
  }

  public void setUserVariables(Set<UserVariable> userVariables) {
    this.userVariables = userVariables;
  }

  public Set<UserMethod> getUserMethods() {
    return this.userMethods;
  }

  public void setUserMethods(Set<UserMethod> userMethods) {
    this.userMethods = userMethods;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

}