package com.javaweb.repository;

import com.javaweb.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository {
    RoleEntity findOneByCode(String code);

    List<RoleEntity> findAll();
}
