package com.example.check_java_oop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
public class UserClass implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(nullable = false)
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
	@ElementCollection
	private List<String> implement;
	@OneToMany(mappedBy = "userClass", cascade = CascadeType.ALL)
	private List<UserVariable> userVariables;
	@OneToMany(mappedBy = "userClass", cascade = CascadeType.ALL)
	private List<UserMethod> userMethods;
	@OneToMany(mappedBy = "userClass", cascade = CascadeType.ALL)
	private List<Fault> faults;

	public UserClass() {
	}
	public int getId() {
		return id;
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

	public List<String> getImplement() {
		return this.implement;
	}

	public void setImplement(List<String> implement) {
		this.implement = implement;
	}

	public List<UserVariable> getUserVariables() {
		return this.userVariables;
	}

	public void setUserVariables(List<UserVariable> userVariables) {
		this.userVariables = userVariables;
	}

	public List<UserMethod> getUserMethods() {
		return this.userMethods;
	}

	public void setUserMethods(List<UserMethod> userMethods) {
		this.userMethods = userMethods;
	}

	@Override
	public String toString() {
		String s;
		s = "<class name=\"" + name + "\" " + "modifier=\"" + modifier + "\" " + "extends=\"" + extend + "\" "
				+ "implements=\"";

		for (int i = 0; i < implement.size(); i++) {
			if (i == implement.size() - 1) {
				s += implement.get(i);
				break;
			}
			s += implement.get(i) + " ";
		}
		s += "\">\n";
		return s;
	}

}