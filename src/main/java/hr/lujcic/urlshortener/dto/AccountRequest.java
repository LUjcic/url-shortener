package hr.lujcic.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

/**
 * DTO used in reqeusts to /account
 */
public class AccountRequest {

    @JsonProperty("AccountId")
    @NotNull
    private String accountId;

    public AccountRequest() {
    }

    public AccountRequest(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
