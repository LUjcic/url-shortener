package hr.lujcic.urlshortener.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Object for storing and handling redirect info. Contains id, original url that should be redirected to, short url code given to user to use instead of original,
 * status code used in redirecting, account that the redirect is connected to and number of uses/visits to the short url
 */
@Entity
@Table(name = "redirect")
public class Redirect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "original_url", nullable = false)
    private String originalURL;
    @Column(name = "short_url", nullable = false)
    private String shortURL;
    @Column(name = "status_code", nullable = false)
    private Integer statusCode;
    @Column(name = "visits", nullable = false)
    private Integer visits = 0;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    public Redirect() {
    }

    public Redirect(String originalURL, String shortURL, Integer statusCode, Account account) {
        this.originalURL = originalURL;
        this.shortURL = shortURL;
        this.statusCode = statusCode;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalURL() {
        return originalURL;
    }

    public void setOriginalURL(String originalURL) {
        this.originalURL = originalURL;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
