package com.javaweb.repository;

import com.javaweb.entity.CustomerEntity;

import java.util.List;

public interface CustomerRepository {
    int countByIdIn(List<Long> ids);

    CustomerEntity findById(Long id);

    void save(CustomerEntity customer);
}
