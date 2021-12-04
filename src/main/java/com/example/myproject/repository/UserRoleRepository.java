package com.example.myproject.repository;

import com.example.myproject.model.entities.UserRoleEntity;
import com.example.myproject.model.entities.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    UserRoleEntity findUserRoleEntityByRole(UserRoleEnum role);
}
