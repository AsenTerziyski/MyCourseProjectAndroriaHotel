package com.example.myproject.service;

import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.service.UserRegistrationServiceModel;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    void initUsers();

    boolean findUserByUsernameAndPassword(String username, String password);
    UserEntity findUserByUsername(String username);

    void registerNewUser(UserRegistrationServiceModel userRegistrationServiceModel);

    boolean checkIfUsernameExists(String username);

    boolean userIsAdmin(UserEntity userEntity);
    boolean principalIsAdmin(Principal principal);

    boolean delete(String username);

    List<UserEntity> findAllUsers();

//    void deleteUser(UserEntity userByUsername);
}
