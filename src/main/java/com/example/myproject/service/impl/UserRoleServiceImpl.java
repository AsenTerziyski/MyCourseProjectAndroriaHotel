package com.example.myproject.service.impl;

import com.example.myproject.model.entities.UserRoleEntity;
import com.example.myproject.model.entities.enums.UserRoleEnum;
import com.example.myproject.repository.UserRoleRepository;
import com.example.myproject.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void initUserRoles() {
        if (this.userRoleRepository.count() == 0) {
            UserRoleEnum[] values = UserRoleEnum.values();
            for (UserRoleEnum value : values) {
                String role = value.toString().toUpperCase();
                UserRoleEntity userRoleEntity = new UserRoleEntity();
                switch (role) {
                    case "USER":
                        userRoleEntity.setRole(value);
                        break;
                    case "ADMIN":
                        userRoleEntity.setRole(value);
                        break;
                }
                this.userRoleRepository.save(userRoleEntity);
            }
        }
    }
}
