package com.example.check_java_oop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "user_variable")
public class UserVariable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_variable_id")
  private int id;
  @Column(name = "name")
  @NotNull
  private String name;
  @Column(name = "type")
  @NotNull
  private String type;
  @Column(name = "modifier")
  @NotNull
  private String modifier;
  @ManyToOne
  @JoinColumn(name = "user_method_id")
  private UserMethod userMethod;
  @ManyToOne
  @JoinColumn(name = "user_class_id")
  private UserClass userClass;

  public UserVariable() {
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getModifier() {
    return this.modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public UserMethod getUserMethod() {
    return this.userMethod;
  }

  public void setUserMethod(UserMethod userMethod) {
    this.userMethod = userMethod;
  }

  public UserClass getUserClass() {
    return this.userClass;
  }

  public void setUserClass(UserClass userClass) {
    this.userClass = userClass;
  }

}