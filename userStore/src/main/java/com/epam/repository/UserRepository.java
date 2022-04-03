package com.epam.repository;

import org.springframework.data.repository.CrudRepository;

import com.epam.entities.User;

public interface UserRepository extends CrudRepository<User, String>{

}
