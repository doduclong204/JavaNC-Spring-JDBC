package com.javaweb.repository.impl;

import com.javaweb.entity.TransactionEntity;
import com.javaweb.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TransactionEntity> findByCodeAndCustomerId(String code, Long customerId) {
        String sql = "SELECT * FROM transaction WHERE code = ? AND customer_id = ?";
        return jdbcTemplate.query(sql, new Object[]{code, customerId}, new BeanPropertyRowMapper<>(TransactionEntity.class));
    }
}

