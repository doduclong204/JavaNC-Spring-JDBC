package com.javaweb.repository.impl;

import com.javaweb.entity.RoleEntity;
import com.javaweb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public RoleEntity findOneByCode(String code) {
        String sql = "SELECT * FROM role WHERE code = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{code}, new BeanPropertyRowMapper<>(RoleEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    @Override
    public List<RoleEntity> findAll() {
        String sql = "SELECT * FROM role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RoleEntity.class));
    }
}


