package org.quickmonie.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.Test;
import org.quickmonie.core.dto.request.AccountDepositRequest;
import org.quickmonie.core.dto.request.UpdateAccountRequest;
import org.quickmonie.core.dto.response.AccountDepositResponse;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.dto.response.UpdateAccountResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.exception.AccountUpdateFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
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

    @Test
    public void testCanUpdateAccountKycDocument() {
        String accountId = "0f88c24e-8f30-4903-9a14-0e5de7c8e756";
        String accountNumber = "0123456789";
        try (InputStream kycDocumentStream = Files.newInputStream(Path
                .of("/Users/dee/Desktop/quickmonie/src/main/resources/static/osimehn.jpeg"));){
            MultipartFile file = new MockMultipartFile("kyc_document", kycDocumentStream);
            List<JsonPatchOperation> operations = List.of(
                    new ReplaceOperation(
                            new JsonPointer("/email"),
                           new TextNode("james@email.com")
                    ),
                    new ReplaceOperation(
                            new JsonPointer("/balance"),
                            new TextNode(new BigDecimal(10_000_000).toString())
                    )
            );
            UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
            updateAccountRequest.setVerificationDocument(file);
            JsonPatch patch = new JsonPatch(operations);
            updateAccountRequest.setPatch(objectMapper.writeValueAsString(patch));
            UpdateAccountResponse response = accountService.updateAccount(accountId, updateAccountRequest);
            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isNotNull();
            AccountResponse accountResponse  = accountService.getAccount(accountNumber);
            assertThat(accountResponse).isNotNull();
            assertThat(accountResponse.getEmail()).isNotNull();
            assertThat(accountResponse.getKycDocument()).isNotNull();
        }catch (IOException | JsonPointerException | AccountUpdateFailedException exception){
            exception.printStackTrace();
            assertThat(exception).isNull();
        } catch (AccountNotFoundException ex) {
            assertThat(ex).isNull();
        }
    }

    @Test
    public void testCanVerifyAccount() {

    }







}






