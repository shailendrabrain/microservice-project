package com.epam.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.UserDto;
import com.epam.exceptions.UserException;
import com.epam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	private ObjectMapper objectMapper = new ObjectMapper();

	UserDto userDto;

	@BeforeEach
	public void setup() {
		userDto = new UserDto();
		userDto.setEmail("abc@gmail.com");
		userDto.setName("abc");
		userDto.setUserName("abc");
	}

	@Test
	void testGetUsers() throws Exception {

		List<UserDto> usersDto = Arrays.asList(userDto);

		when(userService.getUsers()).thenReturn(usersDto);

		mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)));

	}

	@Test
	void testStoreUser() throws Exception {

		when(userService.storeUser(userDto)).thenReturn(userDto);

		String UserJson = objectMapper.writeValueAsString(userDto);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(UserJson)).andDo(print())
				.andExpect(status().isCreated()); 
	}
	
	
	@Test
	void testStoreUserExcep() throws Exception {

		UserDto u=new UserDto();
		when(userService.storeUser(u)).thenReturn(u);
 
		String UserJson = objectMapper.writeValueAsString(u);

		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(UserJson)).andDo(print())
				.andExpect(status().isBadRequest()); 
	}

	@Test
	void testGetUser() throws Exception {
		when(userService.getUser(Mockito.anyString())).thenReturn(userDto);
		mockMvc.perform(get("/users/{id}", 3)).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", Matchers.equalTo(userDto.getName())));

	}

	@Test
	void testUpdateUser() throws Exception {
		String userJson = objectMapper.writeValueAsString(userDto);

		mockMvc.perform((put("/users/{userName}", "abc").contentType(MediaType.APPLICATION_JSON).content(userJson)))
				.andExpect(status().isOk());
	}

	@Test
	void testDeleteUser() throws Exception { 
		
			mockMvc.perform(delete("/users/{userName}", "abc")).andExpect(status().isNoContent());
		
	}
	
	@Test
	void testDeleteUserExcep() throws Exception {

		doThrow(new UserException("user not found")).when(userService).deleteUser(Mockito.anyString());
		mockMvc.perform(delete("/users/{userName}", "abc")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error", Matchers.equalTo("user not found")));

	}
	

}
