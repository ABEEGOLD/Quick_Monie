package org.quickmonie.core.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDepositResponse {
    private boolean status;
    private LocalDateTime timestamp;
    private BigDecimal amount;

    public AccountDepositResponse() {
        timestamp = LocalDateTime.now();
    }
}
