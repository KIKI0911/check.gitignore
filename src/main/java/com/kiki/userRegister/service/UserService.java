package com.kiki.userRegister.service;

import com.kiki.userRegister.entity.User;
import com.kiki.userRegister.exception.UserAlreadyExistsException;
import com.kiki.userRegister.exception.UserNotFoundException;
import com.kiki.userRegister.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User findUser(Integer id) {
        Optional<User> user_Id = this.userMapper.findById(id);
        if (user_Id.isPresent()) {
            return user_Id.get();
        } else {
            throw new UserNotFoundException("user:" + id + " not found");
        }
    }

    public User findUserFromAddress(Integer id, Integer addressId) {
        Optional<User> user_IdAndAddress = this.userMapper.findByIdAndAddressId(id,addressId);
        if (user_IdAndAddress.isPresent()) {
            return user_IdAndAddress.get();
        } else {
            throw new UserNotFoundException("user:" + id + " not found");
        }
    }

    public User insert(String name, String email, Integer addressId, Integer age) {
        Optional<User> userOptional = this.userMapper.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("name, email, address and age already exists");
        }
        User user = new User(null, name, email, addressId, age);
        userMapper.insert(user);
        return user;
    }
}
