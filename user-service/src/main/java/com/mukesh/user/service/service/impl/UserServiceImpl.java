package com.mukesh.user.service.service.impl;

import com.mukesh.user.service.exception.UserException;
import com.mukesh.user.service.model.User;
import com.mukesh.user.service.payload.dto.KeycloakUserinfo;
import com.mukesh.user.service.repository.UserRepository;
import com.mukesh.user.service.service.KeyclockService;
import com.mukesh.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeyclockService keyclockService;
    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> otp = userRepository.findById(id);
        if(otp.isPresent()){
            return otp.get();
        }
        throw new UserException("User not found ");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void delecteUser(Long id) throws UserException {
        Optional<User> opt = userRepository.findById(id);

        if (opt.isEmpty()){
            throw new UserException("user not found with id"+ id);

        }
        userRepository.deleteById(opt.get().getId());
    }

    @Override
    public User updateUer(Long id, User user) throws UserException {
        Optional<User> opt = userRepository.findById(id);

        if (opt.isEmpty()){
            throw new UserException("user not found with id"+ id);

        }
        User existingUser = opt.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserFromJwtToken(String jwt) throws Exception {
        KeycloakUserinfo userinfo = keyclockService.fetchUserProfileByJwt(jwt);
        return userRepository.findByEmail(userinfo.getEmail());

    }
}
