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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "user_method")
public class UserMethod implements Serializable {

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
	private String returnType;
	@Column
	@NotNull
	private String modifier;
	@Column
	@ElementCollection
	private List<String> parameterType;
	@ManyToOne
	@JoinColumn(name = "user_class_id", nullable = false)
	private UserClass userClass;

	public UserMethod() {
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public List<String> getParameterType() {
		return parameterType;
	}

	public void setParameterType(List<String> parameterType) {
		this.parameterType = parameterType;
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

	public UserClass getUserClass() {
		return this.userClass;
	}

	public void setUserClass(UserClass userClass) {
		this.userClass = userClass;
	}

	@Override
	public String toString() {
		String s;
		s = "    <method name=\"" + name + "\" " + "return=\"" + returnType + "\" "
				+ "modifier=\"" + modifier + "\" " + "parameter_type=\"";

		for (int i = 0; i < parameterType.size(); i++) {
			if (i == parameterType.size() - 1) {
				s += parameterType.get(i);
				break;
			}
			s += parameterType.get(i) + " ";
		}
		s += "\"/>\n";
		return s;
	}

}