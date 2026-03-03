package org.quickmonie.core.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateRequest {
    private String id;
    private String name = "";
    private String email = "";
}
