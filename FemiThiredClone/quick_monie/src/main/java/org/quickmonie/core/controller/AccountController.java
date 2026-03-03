package org.quickmonie.core.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quickmonie.core.dto.request.UpdateAccountRequest;
import org.quickmonie.core.exception.AccountNotFoundException;
import org.quickmonie.core.exception.AccountUpdateFailedException;
import org.quickmonie.core.service.AccountService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PatchMapping(value = "/{accountId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAccount(@PathVariable String accountId, @ModelAttribute UpdateAccountRequest request) throws AccountUpdateFailedException, AccountNotFoundException {
        log.info("req::{}", request);
        return ResponseEntity.ok(accountService.updateAccount(accountId, request));
    }
}
