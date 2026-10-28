package com.mukesh.user.service.service;

import com.mukesh.user.service.exception.UserException;
import com.mukesh.user.service.model.User;

import java.util.List;

public interface UserService {

    User createUser (User user);
    User getUserById(Long id) throws UserException;
    List<User> getAllUsers();
    void delecteUser(Long id) throws UserException;
    User updateUer(Long id, User user) throws UserException;
    User getUserFromJwtToken(String jwt) throws Exception;

}
