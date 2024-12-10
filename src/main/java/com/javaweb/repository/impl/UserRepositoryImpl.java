package com.javaweb.repository.impl;

import com.javaweb.entity.UserEntity;
import com.javaweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findOneByUserNameAndStatus(String name, int status) {
        String sql = "SELECT * FROM user WHERE username = ? AND status = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name, status}, new BeanPropertyRowMapper<>(UserEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    @Override
    public Page<UserEntity> findByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(String userName, String fullName, int status, Pageable pageable) {
        String sql = "SELECT * FROM user WHERE (username LIKE ? OR fullname LIKE ?) AND status != ?";
        String countSql = "SELECT count(*) FROM user WHERE (username LIKE ? OR fullname LIKE ?) AND status != ?";
        String searchTerm = "%" + userName + "%";
        String fullNameTerm = "%" + fullName + "%";

        List<UserEntity> users = jdbcTemplate.query(sql, new Object[]{searchTerm, fullNameTerm, status},
                new BeanPropertyRowMapper<>(UserEntity.class));

        int total = jdbcTemplate.queryForObject(countSql, new Object[]{searchTerm, fullNameTerm, status}, Integer.class);

        return new PageImpl<>(users, pageable, total);
    }

    @Override
    public List<UserEntity> findByStatusAndRoles_Code(Integer status, String roleCode) {
        String sql = "SELECT u.* FROM user u JOIN user_role ur ON u.id = ur.user_id JOIN role r ON ur.role_id = r.id WHERE u.status = ? AND r.code = ?";
        return jdbcTemplate.query(sql, new Object[]{status, roleCode}, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public Page<UserEntity> findByStatusNot(int status, Pageable pageable) {
        String sql = "SELECT * FROM user WHERE status != ?";
        String countSql = "SELECT count(*) FROM user WHERE status != ?";

        List<UserEntity> users = jdbcTemplate.query(sql, new Object[]{status}, new BeanPropertyRowMapper<>(UserEntity.class));
        int total = jdbcTemplate.queryForObject(countSql, new Object[]{status}, Integer.class);

        return new PageImpl<>(users, pageable, total);
    }

    @Override
    public long countByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(String userName, String fullName, int status) {
        String sql = "SELECT count(*) FROM user WHERE (username LIKE ? OR fullname LIKE ?) AND status != ?";
        String searchTerm = "%" + userName + "%";
        String fullNameTerm = "%" + fullName + "%";
        return jdbcTemplate.queryForObject(sql, new Object[]{searchTerm, fullNameTerm, status}, Long.class);
    }

    @Override
    public long countByStatusNot(int status) {
        String sql = "SELECT count(*) FROM user WHERE status != ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{status}, Long.class);
    }

    @Override
    public UserEntity findOneByUserName(String userName) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(UserEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    @Override
    public UserEntity findByUserName(String userName) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper<>(UserEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    @Override
    public boolean existsByUserName(String userName) {
        String sql = "SELECT count(*) FROM user WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userName}, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public List<UserEntity> findAllById(List<Long> ids) {
        String sql = "SELECT * FROM user WHERE id IN (?)";
        String idsStr = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return jdbcTemplate.query(sql.replace("?", idsStr), new BeanPropertyRowMapper<>(UserEntity.class));
    }

    @Override
    public UserEntity findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(UserEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    @Override
    public UserEntity save(UserEntity user) {
        String sql = "INSERT INTO user (username, fullname, password, status, email) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getStatus());
            ps.setString(5, user.getEmail());
            return ps;
        }, keyHolder);

        Number newId = keyHolder.getKey();
        if (newId != null) {
            user.setId(newId.longValue());
        }

        return user;
    }

}



