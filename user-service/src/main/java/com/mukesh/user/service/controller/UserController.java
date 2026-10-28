package com.mukesh.user.service.controller;

import com.mukesh.user.service.exception.UserException;
import com.mukesh.user.service.mapper.UserMapper;
import com.mukesh.user.service.model.User;
import com.mukesh.user.service.payload.dto.UserDTO;
import com.mukesh.user.service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

//    @Autowired
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user){
        User createUser = userService.createUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);

    }
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> getAllUsers = userService.getAllUsers();
        return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
    }

    @GetMapping("/api/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) throws UserException {
        User getUserById = userService.getUserById(id);
        return new ResponseEntity<>(getUserById, HttpStatus.OK);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateuser(@RequestBody User user,
                                           @PathVariable Long id) throws UserException {
        User createUser = userService.updateUer(id, user);
        return new ResponseEntity<>(createUser, HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) throws UserException {
        userService.delecteUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<UserDTO> getUserFromJwtToken(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.getUserFromJwtToken(jwt);
        UserDTO userDTO=userMapper.mapToDTO(user);


        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

}
