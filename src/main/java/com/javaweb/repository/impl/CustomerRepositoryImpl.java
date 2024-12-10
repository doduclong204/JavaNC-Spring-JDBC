package com.javaweb.repository.impl;

import com.javaweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int countByIdIn(List<Long> ids) {
        String sql = "SELECT COUNT(*) FROM customer WHERE id IN (" +
                ids.stream().map(id -> "?").collect(Collectors.joining(", ")) + ")";
        Object[] params = ids.toArray();
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }
}

