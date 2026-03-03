package org.quickmonie.core.service;

import org.junit.jupiter.api.Test;
import org.quickmonie.core.dto.request.AccountCreateRequest;
import org.quickmonie.core.dto.request.AccountUpdateRequest;
import org.quickmonie.core.dto.response.AccountCreateResponse;
import org.quickmonie.core.dto.response.AccountUpdateResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class AccountServiceCreateUpdateTest {
    @Autowired
    private AccountService accountService;


    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testCanCreateAccount() {
        AccountCreateRequest request = getAccountCreateRequest();

        AccountCreateResponse response = accountService.createAccount(request);

        assertThat(response).isNotNull();

        assertThat(response.getName()).isEqualTo("Tuntun onibudo");
        assertThat(response.getEmail()).isEqualTo("genz@gmail.com");
        assertThat(response.getAccountNumber()).isEqualTo("0123456789");
        assertThat(response.getAccountType()).isEqualTo("PERSONAL");
        assertThat(response.getBalance()).isEqualTo(new BigDecimal("0.00"));

    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testAccountCanBeUpdated() {
        try{
            AccountCreateRequest request = getAccountCreateRequest();
            AccountCreateResponse response = accountService.createAccount(request);
            assertThat(response).isNotNull();
            assertThat(response.getName()).isEqualTo("Tuntun onibudo");
            assertThat(response.getEmail()).isEqualTo("genz@gmail.com");

            AccountUpdateRequest updateRequest = new AccountUpdateRequest();
            updateRequest.setId("0f88c24e-8f30-4903-9a14-0e5de7c8e756");
            updateRequest.setName("Baba Dreamz");
            updateRequest.setEmail("babadreamz@gmail.com");

            AccountUpdateResponse updateResponse = accountService.updateAccount(updateRequest);
            assertThat(updateResponse).isNotNull();
            assertThat(updateResponse.getName()).isEqualTo("Baba Dreamz");
            assertThat(updateResponse.getEmail()).isEqualTo("babadreamz@gmail.com");
        }catch (AccountNotFoundException e){
            assertThat(e).isNull();
        }
    }

    @Test
    @Sql(scripts = {"/db/data.sql"})
    public void testAccountCanBeUpdatedWithNameOnly() {
        try{
            AccountCreateRequest request = getAccountCreateRequest();
            AccountCreateResponse response = accountService.createAccount(request);
            assertThat(response).isNotNull();
            assertThat(response.getName()).isEqualTo("Tuntun onibudo");
            assertThat(response.getEmail()).isEqualTo("genz@gmail.com");

            AccountUpdateRequest updateRequest = new AccountUpdateRequest();
            updateRequest.setId("0f88c24e-8f30-4903-9a14-0e5de7c8e756");
            updateRequest.setName("Baba Dreamz");
            updateRequest.setEmail("babadreamz@gmail.com");

            AccountUpdateResponse updateResponse = accountService.updateAccount(updateRequest);
            assertThat(updateResponse).isNotNull();
            assertThat(updateResponse.getName()).isEqualTo("Baba Dreamz");
            assertThat(updateResponse.getEmail()).isEqualTo("babadreamz@gmail.com");
        } catch (AccountNotFoundException e){
            assertThat(e).isNull();
        }
    }

    private AccountCreateRequest getAccountCreateRequest() {
        AccountCreateRequest request = new AccountCreateRequest();
        request.setName("Tuntun onibudo");
        request.setEmail("genz@gmail.com");
        request.setAccountType("PERSONAL");
        return request;
    }
}
