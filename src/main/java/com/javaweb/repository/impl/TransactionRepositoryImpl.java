package com.javaweb.repository.impl;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TransactionEntity> findByCodeAndCustomerId(String code, Long customerId) {
        String sql = "SELECT * FROM transaction WHERE code = ? AND customerid = ?";
        return jdbcTemplate.query(sql, new Object[]{code, customerId}, new BeanPropertyRowMapper<>(TransactionEntity.class));
    }

    @Override
    public TransactionEntity findById(Long id) {
        String sql = "SELECT * FROM transaction WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(TransactionEntity.class));
        } catch (EmptyResultDataAccessException e) {
            return null; // Trả về null nếu không tìm thấy bản ghi
        }
    }

    @Override
    public void save(TransactionEntity transaction) {
        String sql = "INSERT INTO transaction (code, note, customerid, staffid) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getCode(), transaction.getNote(), transaction.getCustomer().getId(), transaction.getStaffId());
    }
}


