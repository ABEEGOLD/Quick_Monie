package org.quickmonie.core.service;

import org.junit.jupiter.api.Test;
import org.quickmonie.core.dto.request.AccountCreateRequest;
import org.quickmonie.core.dto.request.AccountDepositRequest;
import org.quickmonie.core.dto.response.AccountCreateResponse;
import org.quickmonie.core.dto.response.AccountDepositResponse;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.model.Account;
import org.quickmonie.core.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testCanMakeDeposit() {
        try {
            AccountDepositRequest request = new AccountDepositRequest();
            request.setAccountNumber("0123456789");
            request.setAmount(new BigDecimal("20000"));
            request.setBank("OPAY Digital Services");
            request.setNarration("our very first deposit");

            AccountDepositResponse response = accountService.deposit(request);
            assertThat(response).isNotNull();
            assertThat(response.isStatus()).isTrue();

            AccountResponse account = accountService.getAccount("0123456789");
            assertThat(account).isNotNull();
            assertThat(account.getBalance())
                    .isEqualTo(new BigDecimal("20000.00").toString());
        }catch (AccountNotFoundException e) {
            assertThat(e).isNull();
        }
    }









}






