package org.quickmonie.core.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AccountCreateResponse {
    private String id;
    private String message;
    private String name;
    private String email;
    private String accountType;
    private String accountNumber;
    private BigDecimal balance;

}
