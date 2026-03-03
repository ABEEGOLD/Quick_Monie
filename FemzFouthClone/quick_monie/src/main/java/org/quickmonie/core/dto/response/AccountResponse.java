package org.quickmonie.core.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.quickmonie.core.model.Authority;

import java.util.Set;

@Getter
@Setter
public class AccountResponse {
    private String balance;
    private String kycDocument;
    private String email;
    @JsonIgnore
    private String password;
    private Set<Authority> authorities;
}
