package com.javaweb.repository.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int countByIdIn(List<Long> ids) {
        String sql = "SELECT COUNT(*) FROM customer WHERE id IN (?)";
        String idList = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        return jdbcTemplate.queryForObject(sql.replace("?", idList), Integer.class);
    }

    @Override
    public CustomerEntity findById(Long id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(CustomerEntity.class));
    }

    @Override
    public void save(CustomerEntity customer) {
        String sql = "INSERT INTO customer (fullname, phone, email, companyname, demand, status, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, customer.getFullName(), customer.getPhone(), customer.getEmail(),
                customer.getCompanyName(), customer.getDemand(), customer.getStatus(), customer.getIsActive());
    }
}

