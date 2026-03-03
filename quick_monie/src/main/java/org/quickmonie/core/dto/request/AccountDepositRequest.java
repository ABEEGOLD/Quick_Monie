package org.quickmonie.core.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountDepositRequest {
    private String accountNumber;
    private String bank;
    private String narration;
    private BigDecimal amount;
}
