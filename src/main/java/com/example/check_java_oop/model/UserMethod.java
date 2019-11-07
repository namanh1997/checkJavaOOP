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
@Table(name = "user_method")
public class UserMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_method_id")
  private int id;
  @Column(name = "name")
  @NotNull
  private String name;
  @Column(name = "return_type")
  @NotNull
  private String returnType;
  @Column(name = "modifier")
  @NotNull
  private String modifier;
  @OneToMany(mappedBy="user_variable", cascade = CascadeType.ALL)
  private Set<UserVariable> userVariables;
  @ManyToOne
  @JoinColumn(name = "user_class_id", nullable = false)
  private UserClass userClass;

  public UserMethod() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getReturnType() {
    return this.returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public String getModifier() {
    return this.modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public Set<UserVariable> getUserVariables() {
    return this.userVariables;
  }

  public void setUserVariables(Set<UserVariable> userVariables) {
    this.userVariables = userVariables;
  }

  public UserClass getUserClass() {
    return this.userClass;
  }

  public void setUserClass(UserClass userClass) {
    this.userClass = userClass;
  }

}