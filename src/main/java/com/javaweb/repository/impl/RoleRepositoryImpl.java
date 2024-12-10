package com.javaweb.repository.impl;

import com.javaweb.entity.RoleEntity;
import com.javaweb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RoleEntity findOneByCode(String code) {
        String sql = "SELECT * FROM role WHERE code = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<>(RoleEntity.class));
    }
}

