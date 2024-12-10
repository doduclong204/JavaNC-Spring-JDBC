package com.javaweb.repository;

import com.javaweb.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository {
    List<TransactionEntity> findByCodeAndCustomerId(String code, Long customerId);

    TransactionEntity findById(Long id);

    void save(TransactionEntity transaction);
}
