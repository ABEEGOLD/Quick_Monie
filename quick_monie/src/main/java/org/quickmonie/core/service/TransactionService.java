package org.quickmonie.core.service;

import org.quickmonie.core.dto.request.CreateTransactionRequest;
import org.quickmonie.core.dto.response.CreateTransactionResponse;
import org.quickmonie.core.dto.response.TransactionResponse;
import org.quickmonie.core.exception.TransactionNotFoundException;

public interface TransactionService {

    CreateTransactionResponse create(CreateTransactionRequest transactionRequest);

    TransactionResponse getBy(String transactionId) throws TransactionNotFoundException;
}
