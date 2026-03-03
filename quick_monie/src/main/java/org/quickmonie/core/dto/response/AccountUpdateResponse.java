package org.quickmonie.core.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateResponse {
    private String accountId;
    private String message;
    private String name;
    private String email;
}
