package org.quickmonie.core.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateTransactionRequest {
    private String amount;
    private String accountNumber;
    private String transactionType;
    private String description;
}
