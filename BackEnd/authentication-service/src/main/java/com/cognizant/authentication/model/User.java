package com.cognizant.authentication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name = "us_id")
	private long id;
	
	@NotBlank
	@NotNull
	@Column(name = "us_name")
	private String userName;
	@NotBlank
	@NotNull
	@Column(name = "us_password")
	private String password;
	@NotBlank
	@NotNull
	@Email(message="Invalid e-Mail")
	@Column(name = "us_email")
	private String email;
	@NotBlank
	@NotNull
	@Column(name = "us_mobile_number")
	private String mobileNumber;
	@ManyToOne
	@JoinColumn(name="us_ro_id")
	private Role role;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", role=" + role + "]";
	}
	
		

}
