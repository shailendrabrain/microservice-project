package com.epam.restcontrollers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.UserDto;
import com.epam.service.UserService;

@RestController
public class UserRestController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("users")
	public ResponseEntity<List<UserDto>> getUsers()
	{
		return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
	}
	
	
	@PostMapping("users")
	public ResponseEntity<UserDto> storeUser(@RequestBody @Valid UserDto userDto)
	{
		return new ResponseEntity<>(userService.storeUser(userDto),HttpStatus.CREATED);
	}
	
	@GetMapping("users/{userName}")
	public ResponseEntity<UserDto> getUser(@PathVariable String userName)
	{
		return new ResponseEntity<>(userService.getUser(userName),HttpStatus.OK);
	}
	
	@PutMapping("users/{userName}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String userName, @RequestBody @Valid UserDto userDto)
	{
		return new ResponseEntity<>(userService.updateUser(userName, userDto),HttpStatus.OK);
	}
	
	@DeleteMapping("users/{userName}")
	public ResponseEntity<Object> deleteUser(@PathVariable String userName)
	{
		userService.deleteUser(userName);
		return ResponseEntity.noContent().build();
	}
	

}
