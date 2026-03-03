package org.quickmonie.core.service;

import org.junit.jupiter.api.Test;
import org.quickmonie.core.dto.request.CreateTransactionRequest;
import org.quickmonie.core.dto.response.CreateTransactionResponse;
import org.quickmonie.core.dto.response.TransactionResponse;
import org.quickmonie.core.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void testCanCreateTransaction() {
        try {
            String amount = new BigDecimal(20_000).toString();
            String accountNumber = "0123456789";
            String transactionType = "CREDIT";
            String description = "our very first deposit";
            CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
            transactionRequest.setAccountNumber(accountNumber);
            transactionRequest.setDescription(description);
            transactionRequest.setAmount(amount);
            transactionRequest.setTransactionType(transactionType);
            CreateTransactionResponse transactionResponse = transactionService.create(transactionRequest);
            assertThat(transactionResponse).isNotNull();
            assertThat(transactionService.getBy(transactionResponse.getId()));
        }catch (TransactionNotFoundException e) {
            e.printStackTrace();
            assertThat(e).isNull();
        }
    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testCanGetTransaction() {
        try {
            String transactionId = "9059ca01-3ee1-46ec-a509-b2819f164734";

            TransactionResponse transaction = transactionService.getBy(transactionId);
            assertThat(transaction).isNotNull();
            assertThat(transaction.getTransactionId()).isEqualTo(transactionId);
        }catch (TransactionNotFoundException exception){
            assertThat(exception).isNull();
        }
    }

}
