package com.spring.practice.bean;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestBean implements Serializable {

	private static final long serialVersionUID = 4310048071638217241L;

	@NotBlank(message = "Name must be filled out..!!")
	private String name;
	@NotBlank(message = "Email must be filled out..!!")
	@Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
	private String email;
	@NotBlank(message = "Mobile must be filled out..!!")
	@Pattern(regexp="(^$|[0-9]{10})")
	@Size(min=10,max=10,message = "Mobile should be 10 digits..!!")
	private String mobile;
	@NotBlank(message = "Password must be filled out..!!")
	private String password;
	@NotBlank(message = "Username must be filled out..!!")
	private String username;
	@NotBlank(message = "Username must be filled out..!!")
	private String role;
}
