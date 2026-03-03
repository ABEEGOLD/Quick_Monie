package org.quickmonie.core.repository;

import org.junit.jupiter.api.Test;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Test
    @Sql(scripts = {"/db/data.sql"})
    void getByAccountNumberTest() {
        try {
            Account account = accountRepository.getByAccountNumber("0123456789")
                    .orElseThrow(() ->
                            new AccountNotFoundException(
                                    "Account number not found for given account"
                            ));
            assertThat(account).isNotNull();
        } catch (AccountNotFoundException e) {
            e.printStackTrace();
            assertThat(e).isNull();
        }

    }
}