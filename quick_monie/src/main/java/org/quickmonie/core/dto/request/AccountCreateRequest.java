package org.quickmonie.core.dto.request;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AccountCreateRequest {
    private String name;
    private String email;
    private String accountType;
}
