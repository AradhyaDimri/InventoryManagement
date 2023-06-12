package com.InventoryManagement.Services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.InventoryManagement.Exceptions.ResourceNotFoundException;
import com.InventoryManagement.Payloads.Userdatatransfer;
import com.InventoryManagement.Services.UserService;
import com.InventoryManagement.entities.User;
import com.InventoryManagement.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Userdatatransfer CreateUser(Userdatatransfer userdto) {
        User user = dtoToUser(userdto);
        User savedUser = userRepo.save(user);
        return usertoDto(savedUser);
    }

    @Override
    public Userdatatransfer updateUser(Userdatatransfer userdto, Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setAddress(userdto.getAddress());
        user.setAbout(userdto.getAbout());

        User updatedUser = userRepo.save(user);
        return usertoDto(updatedUser);
    }

    @Override
    public Userdatatransfer getUserById(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return usertoDto(user);
        } else {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    @Override
    public List<Userdatatransfer> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(this::usertoDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepo.delete(user);
    }

    private User dtoToUser(Userdatatransfer userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());
        user.setAbout(userDTO.getAbout());
        user.setAddress(userDTO.getAddress());
        return user;
    }

    private Userdatatransfer usertoDto(User user) {
        Userdatatransfer userDto = new Userdatatransfer();
        userDto.setName(user.getName());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setAbout(user.getAbout());
        userDto.setAddress(user.getAddress());
        return userDto;
    }
}
