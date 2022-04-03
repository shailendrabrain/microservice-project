package com.epam.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.epam.dto.UserDto;
import com.epam.entities.User;
import com.epam.exceptions.UserException;
import com.epam.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepo;

	@InjectMocks
	UserService userService;
	
	@Mock
	ModelMapper modelMapper;

	User user;
	UserDto userDto;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setEmail("abc@gmail.com");
		user.setName("abc");
		user.setUserName("abc");

		userDto = new UserDto();
		userDto.setEmail("abc@gmail.com");
		userDto.setName("abc");
		userDto.setUserName("abc");

	}

	@Test
	void testGetUsers() {
		List<User> users = Arrays.asList(user);
		when(userRepo.findAll()).thenReturn(users);
		
		List<UserDto>usersDto=Arrays.asList(userDto);
		when(modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType())).thenReturn(usersDto);
		assertEquals(users.size(), userService.getUsers().size());

	}

	@Test
	void testStoreUser() {
		
		when(userRepo.save(user)).thenReturn(user);
		when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
		when(modelMapper.map(userDto, User.class)).thenReturn(user);
		
		assertNotNull(userService.storeUser(userDto));

		
	}

	@Test
	void testGetUser() {
		Optional<User> opt=Optional.ofNullable(user);
		when(userRepo.findById(anyString())).thenReturn(opt);
		when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
		assertNotNull(userService.getUser("abc"));
	
	}
	
	@Test
	void testGetUserEx() {
		when(userRepo.findById(anyString())).thenThrow(new UserException("user not found"));
      assertThrows(UserException.class, ()->userService.getUser("abc"));
	}

	@Test
	void testDeleteUser() {
		userService.deleteUser("abc");
		verify(userRepo).deleteById(anyString());
	}
	

	@Test
	void testDeleteUserEx() {
		userService.deleteUser("abc");
		doThrow(new UserException("user not found")).when(userRepo).deleteById(anyString());
      assertThrows(UserException.class, ()->userService.deleteUser("abc"));
	}
	

	@Test
	void testUpdateUser() {
		
		when(userRepo.existsById(anyString())).thenReturn(true);
		when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
		when(modelMapper.map(userDto, User.class)).thenReturn(user);
		assertNotNull(userService.updateUser("abc", userDto));
	}

	@Test
	void testUpdateUserExp() {
		
		when(userRepo.existsById(anyString())).thenReturn(false);
	  assertThrows(UserException.class, ()->userService.updateUser("abc", userDto));
	}

	
}
