package com.example.check_java_oop.model;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class UserClass {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private int id;
  @Column
  @NotNull
  private String name;
  @Column
  @NotNull
  private String modifier;
  @Column
  private String extend;
  @Column
  private ArrayList<String> implement;
  @OneToMany(mappedBy="userClass", cascade = CascadeType.ALL)
  private ArrayList<UserVariable> userVariables;
  @OneToMany(mappedBy="userClass", cascade = CascadeType.ALL)
  private ArrayList<UserMethod> userMethods;
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


  public ArrayList<String> getImplement() {
    return this.implement;
  }

  public void setImplement(ArrayList<String> implement) {
    this.implement = implement;
  }

  public ArrayList<UserVariable> getUserVariables() {
    return this.userVariables;
  }

  public void setUserVariables(ArrayList<UserVariable> userVariables) {
    this.userVariables = userVariables;
  }

  public ArrayList<UserMethod> getUserMethods() {
    return this.userMethods;
  }

  public void setUserMethods(ArrayList<UserMethod> userMethods) {
    this.userMethods = userMethods;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString(){
    String s;
    s = "<class name=\"" + name +"\" "
            +"modifier=\"" + modifier +"\" "
            +"extends=\"" + extend +"\" "
            +"implements=\"" ;
    
    for(int i = 0; i < implement.size() ; i++){
        if(i == implement.size() - 1){
            s += implement.get(i);
            break;
        }
        s += implement.get(i) +" ";
    }
    s += "\">\n";
    return s;
  }

}