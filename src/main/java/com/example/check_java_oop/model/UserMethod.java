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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "user_method")
public class UserMethod {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private int id;
  @Column
  @NotNull
  private String name;
  @Column(name = "return_type")
  @NotNull
  private String returnType;
  @Column
  @NotNull
  private String modifier;
  @OneToMany(mappedBy="userMethod", cascade = CascadeType.ALL)
  private ArrayList<UserVariable> userVariables;
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

  public ArrayList<UserVariable> getUserVariables() {
    return this.userVariables;
  }

  public void setUserVariables(ArrayList<UserVariable> userVariables) {
    this.userVariables = userVariables;
  }

  public UserClass getUserClass() {
    return this.userClass;
  }

  public void setUserClass(UserClass userClass) {
    this.userClass = userClass;
  }

  @Override
  public String toString(){
    String s;
    s = "    <method name=\"" + name +"\" "
            +"return=\"" + returnType +"\" "
            +"modifier=\"" + modifier +"\" "
            +"parameter_type=\"" ;
    
    for(int i = 0; i < userVariables.size() ; i++){
        if(i == userVariables.size() - 1){
            s += userVariables.get(i);
            break;
        }
        s += userVariables.get(i) +" ";
    }
    s += "\"/>\n";
    return s;
  }

}