package hr.lujcic.urlshortener.exceptions;

/**
 * Thrown when account couldn't be found
 */
public class AccountNotFoundException extends Exception{

    public AccountNotFoundException(String message) {
        super(message);
    }
}
