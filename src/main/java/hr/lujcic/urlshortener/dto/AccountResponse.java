package hr.lujcic.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO used in /account response
 */
public class AccountResponse {
    private boolean success;
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    public AccountResponse(boolean success, String description, String password) {
        this.success = success;
        this.description = description;
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
