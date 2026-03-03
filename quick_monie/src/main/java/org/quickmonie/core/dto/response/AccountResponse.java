package org.quickmonie.core.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private String balance;
    private String kycDocument;
    private String email;
}
