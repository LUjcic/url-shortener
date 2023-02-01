package hr.lujcic.urlshortener.dto;

/**
 * Dto used in register response
 */
public class RegisterResponse {
    private String shortUrl;

    public RegisterResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
