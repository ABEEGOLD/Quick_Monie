package org.quickmonie.core.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateAccountResponse {
    private String accountId;
    private String message;
}
