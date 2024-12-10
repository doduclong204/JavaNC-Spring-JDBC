package com.javaweb.repository.impl;

import com.javaweb.entity.UserEntity;
import com.javaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserEntity findOneByUserNameAndStatus(String name, int status) {
        String sql = "SELECT * FROM user WHERE username = ? AND status = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{name, status}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public Page<UserEntity> findByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(
            String userName, String fullName, int status, Pageable pageable) {
        String sql = "SELECT * FROM user WHERE (username LIKE ? OR full_name LIKE ?) AND status != ?";
        String searchUserName = "%" + userName + "%";
        String searchFullName = "%" + fullName + "%";
        List<UserEntity> users = jdbcTemplate.query(sql, new Object[]{searchUserName, searchFullName, status},
                new BeanPropertyRowMapper<>(UserEntity.class));
        return new PageImpl<>(users, pageable, users.size());
    }

    @Override
    public List<UserEntity> findByStatusAndRoles_Code(Integer status, String roleCode) {
        String sql = "SELECT * FROM user u JOIN user_roles ur ON u.id = ur.user_id JOIN role r ON ur.role_id = r.id WHERE u.status = ? AND r.code = ?";
        return jdbcTemplate.query(sql, new Object[]{status, roleCode}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public Page<UserEntity> findByStatusNot(int status, Pageable pageable) {
        String sql = "SELECT * FROM user WHERE status != ?";
        List<UserEntity> users = jdbcTemplate.query(sql, new Object[]{status}, new BeanPropertyRowMapper<>(UserEntity.class));
        return new PageImpl<>(users, pageable, users.size());
    }

    @Override
    public long countByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(String userName, String fullName, int status) {
        String sql = "SELECT COUNT(*) FROM user WHERE (username LIKE ? OR full_name LIKE ?) AND status != ?";
        String searchUserName = "%" + userName + "%";
        String searchFullName = "%" + fullName + "%";
        return jdbcTemplate.queryForObject(sql, new Object[]{searchUserName, searchFullName, status}, Long.class);
    }

    @Override
    public long countByStatusNot(int status) {
        String sql = "SELECT COUNT(*) FROM user WHERE status != ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{status}, Long.class);
    }

    @Override
    public UserEntity findOneByUserName(String userName) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public UserEntity findByUserName(String userName) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public boolean existsByUserName(String userName) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userName}, Integer.class);
        return count != null && count > 0;
    }
}

