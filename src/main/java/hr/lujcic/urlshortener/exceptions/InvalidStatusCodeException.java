package hr.lujcic.urlshortener.exceptions;

/**
 * Thrown when an invalid status code is given
 */
public class InvalidStatusCodeException extends Exception {
    public InvalidStatusCodeException(String m) {
        super(m);
    }
}
