package hr.lujcic.urlshortener.exceptions;

public class NoAuthorizationTokenException extends Exception {

    public NoAuthorizationTokenException(String message) {
        super(message);
    }
}
