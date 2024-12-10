package com.javaweb.service.impl;

import com.javaweb.converter.TransactionConverter;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.response.TransactionResponse;
import com.javaweb.repository.TransactionRepository;
import com.javaweb.service.ITransactionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService implements ITransactionService {
    TransactionRepository transactionRepository;
    TransactionConverter transactionConverter;

    @Override
    public List<TransactionResponse> findByCodeAndCustomerId(String code, Long customerId) {
        List<TransactionEntity> transactions = transactionRepository.findByCodeAndCustomerId(code, customerId);
        return transactions.stream().map(transactionConverter::convertToResponse
        ).collect(Collectors.toList());
    }

    @Override
    public TransactionDTO loadTransactionDetail(Long id) {
        return transactionConverter.convertToDto(transactionRepository.findById(id));
    }

    @Override
    public void addOrUpdateTransaction(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = transactionConverter.convertToEntity(transactionDTO);
        transactionRepository.save(transactionEntity);
    }
}
