package com.epam.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	@NotNull(message="username can not be null")
	@NotEmpty(message = "username can not be empty")
	String userName;
	
	@NotNull(message="name can not be null")
	@NotEmpty(message = "name can not be empty")
	String name;
	
	@NotNull(message="email can not be null")
	@NotEmpty(message = "email can not be empty")
	@Email
	String email;
	
}
