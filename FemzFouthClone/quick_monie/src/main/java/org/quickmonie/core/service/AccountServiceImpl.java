package org.quickmonie.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.quickmonie.core.dto.request.AccountDepositRequest;
import org.quickmonie.core.dto.request.CreateTransactionRequest;
import org.quickmonie.core.dto.request.UpdateAccountRequest;
import org.quickmonie.core.dto.response.AccountDepositResponse;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.dto.response.CloudUploadResponse;
import org.quickmonie.core.dto.response.UpdateAccountResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.exception.AccountUpdateFailedException;
import org.quickmonie.core.exception.FileUploadException;
import org.quickmonie.core.model.Account;
import org.quickmonie.core.proxy.cloud.CloudService;
import org.quickmonie.core.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;
    private final CloudService cloudService;

    @Override
    public AccountDepositResponse deposit(AccountDepositRequest request) throws AccountNotFoundException {
        Account account = findAccountBy(request.getAccountNumber());
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);
        transactionService.create(buildTransactionRequestFor(request));
        AccountDepositResponse response = new AccountDepositResponse();
        response.setAmount(request.getAmount());
        response.setStatus(true);

        return response;
    }

    private CreateTransactionRequest buildTransactionRequestFor(AccountDepositRequest request) {
        CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
        transactionRequest.setAccountNumber(request.getAccountNumber());
        transactionRequest.setTransactionType("DEPOSIT");
        transactionRequest.setDescription(transactionRequest.getDescription());
        return transactionRequest;
    }



    @Override
    public AccountResponse getAccount(String number) throws AccountNotFoundException {
        Account account = findAccountBy(number);
        return modelMapper.map(account, AccountResponse.class);
    }

    @Override
    public UpdateAccountResponse updateAccount(String accountId, UpdateAccountRequest updateAccountRequest) throws AccountNotFoundException, AccountUpdateFailedException {
        try {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("account not found"));
            UpdateAccountResponse response = modelMapper.map(account, UpdateAccountResponse.class);

            if (updateAccountRequest.getVerificationDocument() != null) {
                CloudUploadResponse cloudUploadResponse = cloudService.upload(updateAccountRequest.getVerificationDocument());
                account.setVerificationDocument(cloudUploadResponse.getUrl());
            }
            JsonNode accountNode = objectMapper.convertValue(account, JsonNode.class);
            JsonPatch patch = updateAccountRequest.getPatch();
            JsonNode updated = patch.apply(accountNode);
            account = objectMapper.convertValue(updated, Account.class);
            accountRepository.save(account);
            response.setMessage("account update successful");
            log.info("Updated account:: {}", response);
            return response;
        }catch (JsonPatchException | FileUploadException ex){
            throw new AccountUpdateFailedException(ex.getMessage());
        }
    }

    @Override
    public AccountResponse getBy(String username) throws AccountNotFoundException {
        Account account =  accountRepository.findByEmail(username)
                                            .orElseThrow(()->new AccountNotFoundException(
                                                    String.format("account %s not found", username)
                                            ));
        return modelMapper.map(account, AccountResponse.class);
    }

    private Account findAccountBy(String accountNumber) throws AccountNotFoundException {
        return accountRepository.getByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                String.format("account %s not found", accountNumber)
                        ));
    }
}
