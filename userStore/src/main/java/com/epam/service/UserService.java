package com.epam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.epam.dto.UserDto;
import com.epam.entities.User;
import com.epam.exceptions.UserException;
import com.epam.repository.UserRepository;

@Controller
public class UserService {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
   UserRepository userRepo;
	
	public List<UserDto> getUsers()
	{
		List<User>users= (List<User>) userRepo.findAll();		
		return modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
	}
	
	public UserDto storeUser(UserDto userDto)
	{
		User user=modelMapper.map(userDto, User.class);
		user=userRepo.save(user);
		return modelMapper.map(user, UserDto.class);	
	}
	
	public UserDto getUser(String  userName)
	{
		User user=userRepo.findById(userName).orElseThrow(()->new UserException("user not found"));
		return modelMapper.map(user, UserDto.class);
	}
	
	public void deleteUser(String userName)
	{
		try {
			userRepo.deleteById(userName);
		}
		catch (Exception e) {
		 throw new UserException("user not found");
		}
	}
	
	public UserDto updateUser(String userName, UserDto userDto)
	{
		if(userRepo.existsById(userName))
		{
			userDto.setUserName(userName);
			User user=modelMapper.map(userDto, User.class);
			userRepo.save(user);
			userDto=modelMapper.map(user, UserDto.class);
		}
		else
		{
			throw new UserException("user not found");
		}
		return userDto;
	}
	
	

}
