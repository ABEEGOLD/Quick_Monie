package org.quickmonie.core.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.quickmonie.core.dto.request.AccountCreateRequest;
import org.quickmonie.core.dto.request.AccountDepositRequest;
import org.quickmonie.core.dto.request.AccountUpdateRequest;
import org.quickmonie.core.dto.request.CreateTransactionRequest;
import org.quickmonie.core.dto.response.AccountCreateResponse;
import org.quickmonie.core.dto.response.AccountDepositResponse;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.dto.response.AccountUpdateResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.exception.EmptyUpdateException;
import org.quickmonie.core.model.Account;
import org.quickmonie.core.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final TransactionService transactionService;

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

    private Account findAccountBy(String accountNumber) throws AccountNotFoundException {
        return accountRepository.getByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                String.format("Account %s not found", accountNumber)
                        ));
    }

    @Override
    public AccountCreateResponse createAccount(AccountCreateRequest request){
        Account account = new  Account();
        account.setName(request.getName());
        account.setEmail(request.getEmail());
        account.setAccountType(request.getAccountType());
        account.setBalance(new BigDecimal("0.00"));
        account.setAccountNumber("0123456789");

        Account savedAccount = accountRepository.save(account);

        AccountCreateResponse response = modelMapper.map(savedAccount,  AccountCreateResponse.class);
        response.setMessage("Account created successfully");
        return response;
    }

   @Override
    public AccountUpdateResponse updateAccount(AccountUpdateRequest request) throws AccountNotFoundException {
        Account account = accountRepository.findById(request.getId())
                .orElseThrow(
                        () -> new AccountNotFoundException("Account not found")
                );
        boolean nameIsPresent = !(request.getName().isBlank());
        boolean emailIsPresent = !(request.getEmail().isBlank());

        if(!nameIsPresent && !emailIsPresent){
            throw new EmptyUpdateException("Account details cannot be empty");
        }

        if(nameIsPresent){
            account.setName(request.getName());
        }

        if(emailIsPresent){
            account.setEmail(request.getEmail());
        }

        Account updatedAccount = accountRepository.save(account);
        AccountUpdateResponse response = modelMapper.map(updatedAccount,  AccountUpdateResponse.class);
        response.setMessage("Account updated successfully");

        return response;

    }
}
