package org.quickmonie.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.quickmonie.core.dto.request.CreateTransactionRequest;
import org.quickmonie.core.dto.response.CreateTransactionResponse;
import org.quickmonie.core.dto.response.TransactionResponse;
import org.quickmonie.core.exception.TransactionNotFoundException;
import org.quickmonie.core.model.Account;
import org.quickmonie.core.model.Transaction;
import org.quickmonie.core.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public CreateTransactionResponse create(CreateTransactionRequest transactionRequest) {
        Transaction transaction = modelMapper.map(transactionRequest, Transaction.class);
        transaction = transactionRepository.save(transaction);
        return modelMapper.map(transaction, CreateTransactionResponse.class);
    }

    @Override
    public TransactionResponse getBy(String transactionId) throws TransactionNotFoundException {
        Account account = new Account();
        Transaction transaction = transactionRepository.findById(transactionId)
                                   .orElseThrow(
                                           ()-> new TransactionNotFoundException("transaction not found")
                                   );
        log.info("Transaction: {}", transaction);
        TransactionResponse transactionRes =  modelMapper.map(transaction, TransactionResponse.class);
        modelMapper.map(account, transactionRes);
        return transactionRes;
    }
}
