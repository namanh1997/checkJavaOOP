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

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "user_variable")
public class UserVariable {

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
	private String type;
	@Column
	private String modifier;
	@ManyToOne
	@JoinColumn(name = "user_class_id")
	private UserClass userClass;

	public UserVariable() {
	}

	public void setId(int id) {
		this.id = id;
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

	public UserClass getUserClass() {
		return this.userClass;
	}

	public void setUserClass(UserClass userClass) {
		this.userClass = userClass;
	}

	@Override
	public String toString() {
		String s;
		s = "<variable name=\"" + name + "\" " + "type=\"" + type + "\" " + "modifier=\""
				+ modifier + "\"/>\n";
		return s;
	}

}