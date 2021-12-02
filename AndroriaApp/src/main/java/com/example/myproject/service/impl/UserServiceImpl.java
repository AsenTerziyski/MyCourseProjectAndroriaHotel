package com.example.myproject.service.impl;


import com.example.myproject.model.entities.UserEntity;
import com.example.myproject.model.entities.UserRoleEntity;
import com.example.myproject.model.entities.enums.UserRoleEnum;
import com.example.myproject.model.service.UserRegistrationServiceModel;
import com.example.myproject.repository.UserRepository;
import com.example.myproject.repository.UserRoleRepository;
import com.example.myproject.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final AndroriaUserServiceImpl androriaUserService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserRoleRepository userRoleRepository,
                           AndroriaUserServiceImpl androriaUserService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.androriaUserService = androriaUserService;
    }

    @Override
    @Transactional
    public void initUsers() {
        if (this.userRepository.count() == 0) {
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin")
                    .setPassword(this.passwordEncoder.encode("12345"))
                    .setFullName("Admin Adminov")
                    .setAdmin(true);

            UserRoleEnum adminEnum = UserRoleEnum.valueOf("ADMIN");
            UserRoleEnum userEnum = UserRoleEnum.valueOf("USER");

            UserRoleEntity adminRole = this.userRoleRepository.findUserRoleEntityByRole(adminEnum);
            UserRoleEntity userRole = this.userRoleRepository.findUserRoleEntityByRole(userEnum);

            adminUser.setRoles(List.of(adminRole, userRole));
            this.userRepository.save(adminUser);

            UserEntity testUser = new UserEntity();
            testUser.setUsername("tuser").setPassword(this.passwordEncoder.encode("12345"))
                    .setFullName("Test User").setAdmin(false);
            testUser.setRoles(List.of(userRole));
            this.userRepository.save(testUser);

            UserEntity testUser2 = new UserEntity();
            testUser2.setUsername("tuser2").setPassword(this.passwordEncoder.encode("12345"))
                    .setFullName("Test User2").setAdmin(false);
            testUser2.setRoles(List.of(userRole));
            this.userRepository.save(testUser2);

            UserEntity testUser3 = new UserEntity();
            testUser3.setUsername("tuser3").setPassword(this.passwordEncoder.encode("12345"))
                    .setFullName("Test User3").setAdmin(false);
            testUser3.setRoles(List.of(userRole));
            this.userRepository.save(testUser3);

            UserEntity testUser4 = new UserEntity();
            testUser4.setUsername("tuser4").setPassword(this.passwordEncoder.encode("12345"))
                    .setFullName("Test User4").setAdmin(false);
            testUser4.setRoles(List.of(userRole));
            this.userRepository.save(testUser4);
        }
    }

    @Override
    public boolean findUserByUsernameAndPassword(String username, String password) {
        UserEntity userEntity = this.userRepository.findByUsernameAndPassword(username, password).orElse(null);
        if (userEntity == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        return userEntity;
    }

    @Override
    @Transactional
    public void registerNewUser(UserRegistrationServiceModel userRegistrationServiceModel) {
        UserRoleEntity userRole = userRoleRepository
                .findUserRoleEntityByRole(UserRoleEnum.USER);

        UserEntity newUser = new UserEntity();

        String username = userRegistrationServiceModel.getUsername();
        String password = userRegistrationServiceModel.getPassword();
        String fullName = userRegistrationServiceModel.getFullName();

        newUser.setUsername(username)
                .setPassword(passwordEncoder.encode(password))
                .setFullName(fullName)
                .setAdmin(false)
                .setRoles(List.of(userRole));

        this.userRepository.save(newUser);

        UserDetails principal = androriaUserService.loadUserByUsername(newUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (
                        principal,
                        newUser.getPassword(),
                        principal.getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public boolean checkIfUsernameExists(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);
        if (userEntity == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean userIsAdmin(UserEntity userEntity) {
        return userEntity
                .getRoles()
                .stream()
                .map(u->u.getRole()).filter(r->r==UserRoleEnum.ADMIN)
                .findAny()
                .isPresent();
    }

    @Override
    public boolean principalIsAdmin(Principal principal) {
        String name = principal.getName();
        UserEntity userEntity = this.userRepository.findByUsername(name).orElse(null);
        assert userEntity != null;
        return userEntity
                .getRoles()
                .stream()
                .map(UserRoleEntity::getRole).anyMatch(r->r==UserRoleEnum.ADMIN);
    }

    @Override
//    @Transactional
    public boolean delete(String username) {
        UserEntity userEntity = this.userRepository.findByUsername(username).orElse(null);

        if (userEntity != null) {
            this.userRepository.delete(userEntity);
            return true;
        }
        return false;

    }

    @Override
    public List<UserEntity> findAllUsers() {
        return this.userRepository.findAll();
    }

}
