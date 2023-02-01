package hr.lujcic.urlshortener.exceptions;

/**
 * Thrown when trying to create an account with already existing account id
 */
public class AccountExistsException extends Exception {

    public AccountExistsException(String message) {
        super(message);
    }
}
