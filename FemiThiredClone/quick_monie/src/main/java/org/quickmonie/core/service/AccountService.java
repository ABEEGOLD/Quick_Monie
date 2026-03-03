package org.quickmonie.core.service;

import com.github.fge.jsonpatch.JsonPatch;
import org.quickmonie.core.dto.request.AccountDepositRequest;
import org.quickmonie.core.dto.request.UpdateAccountRequest;
import org.quickmonie.core.dto.response.AccountDepositResponse;
import org.quickmonie.core.dto.response.AccountResponse;
import org.quickmonie.core.dto.response.UpdateAccountResponse;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.exception.AccountUpdateFailedException;

public interface AccountService {
    AccountDepositResponse deposit(AccountDepositRequest request) throws AccountNotFoundException;

    AccountResponse getAccount(String number) throws AccountNotFoundException;

    UpdateAccountResponse updateAccount(String accountId, UpdateAccountRequest updateAccountRequest) throws AccountNotFoundException, AccountUpdateFailedException;
}
