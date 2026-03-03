package org.quickmonie.core.service;

import org.quickmonie.core.dto.request.AccountCreateRequest;
import org.quickmonie.core.dto.request.AccountDepositRequest;
import org.quickmonie.core.dto.request.AccountUpdateRequest;
import org.quickmonie.core.dto.response.AccountCreateResponse;
import org.quickmonie.core.dto.response.AccountDepositResponse;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.dto.response.AccountUpdateResponse;
import org.quickmonie.core.exception.AccountNotFoundException;

public interface AccountService {
    AccountDepositResponse deposit(AccountDepositRequest request) throws AccountNotFoundException;

    AccountResponse getAccount(String number) throws AccountNotFoundException;

    AccountCreateResponse createAccount(AccountCreateRequest request);

    AccountUpdateResponse updateAccount(AccountUpdateRequest request) throws AccountNotFoundException;
}