package hr.lujcic.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Dto used in /register requests
 */
public class RegisterRequest {
    @JsonProperty("url")
    @NotNull
    private String url;
    @JsonProperty("redirectType")
    private Integer redirectType = 302;

    public RegisterRequest(String url, int redirectType) {
        this.url = url;
        this.redirectType = redirectType;
    }

    @JsonCreator
    public RegisterRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public RegisterRequest() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }
}
