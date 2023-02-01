package hr.lujcic.urlshortener.exceptions;

/**
 * Thrown when an unauthorized action is performed
 */
public class UnauthorizedException extends Exception{

    public UnauthorizedException(String message) {
        super(message);
    }
}
